package activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.library.databinding.ActivityMainBinding
import library.LibraryItemsCreator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import library.Book
import library.Disc
import library.Library
import library.Newspaper

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: ItemViewModel by viewModels()
    private var isPortrait = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        setFragment()
        observeViewModel()
    }

    private fun setFragment() {
        if (isPortrait) {
            setPortrait()
        } else {
            setLandscape()
        }
    }

    private fun setPortrait() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, ItemListFragment())
            .commit()
    }

    private fun setLandscape() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, ItemListFragment())
            .commit()
    }

    private fun observeViewModel() {
        viewModel.selectedItem.observe(this) { item ->
            item?.let { showInformationFragment(it) }
        }

        viewModel.isAddingNewItem.observe(this) { isAdding ->
            if (isAdding) {
                addNewItemFragment()
            }
        }

        viewModel.shouldCloseFragment.observe(this) {shouldClose ->
            if (shouldClose) {
                supportFragmentManager
                    .popBackStack()
            }
        }
    }

    private fun showInformationFragment(item: Library) {
        val fragment = ItemInformationFragment.newInstance(item, false, null)

        if(isPortrait) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.information_fragment_container, fragment)
                .commit()
        }
    }

    private fun addNewItemFragment() {
        if (isPortrait) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ItemInformationFragment.newInstance(null, true, viewModel.addingItemType.value))
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.information_fragment_container, ItemInformationFragment.newInstance(null, true, viewModel.addingItemType.value))
                .commit()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val isNewPortrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT

        if (isNewPortrait != isPortrait) {
            isPortrait = isNewPortrait
            setFragment()
            viewModel.selectedItem.value?.let {
                showInformationFragment(it)
            } ?: viewModel.isAddingNewItem.value?.takeIf { it }?.let {
                addNewItemFragment()
            }
        }
    }
}