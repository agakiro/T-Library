package activity

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.library.R
import com.example.library.databinding.FragmentItemListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ItemListFragment : Fragment(R.layout.fragment_item_list) {
    private lateinit var binding: FragmentItemListBinding
    private lateinit var adapter: ItemAdapter
    private val viewModel: ItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setObserver()
        setButton()
        setErrorHandler()
    }

    private fun setButton() {
        binding.addButton.setOnClickListener {
            val popup = PopupMenu(context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.popup_menu_item, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.book -> {
                        viewModel.selectNewItemTypeBook()
                        viewModel.startAddingNewItem()
                        true
                    }
                    R.id.newspaper -> {
                        viewModel.selectNewItemTypeNewspaper()
                        viewModel.startAddingNewItem()
                        true
                    }
                    R.id.disc -> {
                        viewModel.selectNewItemTypeDisc()
                        viewModel.startAddingNewItem()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    private fun setRecyclerView() {
        adapter = ItemAdapter { item ->
            viewModel.selectItem(item)
        }
        binding.apply {
            rcView.layoutManager = GridLayoutManager(context, 1)
            rcView.adapter = adapter
        }
        viewModel.scrollPosition.observe(viewLifecycleOwner) { position ->
            (binding.rcView.layoutManager as GridLayoutManager)
                .scrollToPositionWithOffset(position, 0)
        }
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.libraryItems.collect { items ->
                adapter.submitList(items)
            }
        }
    }

    private fun setErrorHandler() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessage.collect { error ->
                error?.let {
                    AlertDialog.Builder(requireContext())
                        .setTitle(viewModel.errorMessage.value)
                        .setMessage("Попробуйте ещё раз")
                        .setPositiveButton("ОК") {_, _ ->
                            if (viewModel.error.value is ArtificialError.LoadingError) viewModel.loadItems()
                            viewModel.clearErrors()
                        }
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val position = (binding.rcView.layoutManager as GridLayoutManager)
            .findFirstVisibleItemPosition()
        viewModel.saveScrollPosition(position)
    }
}