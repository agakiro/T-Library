package activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.library.databinding.ActivityMainBinding
import com.example.library.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import library.Library

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: ItemViewModel by viewModels()
    private var isPortrait = true
    private lateinit var backCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        setFragment()
        observeViewModel()
        setShimmer()

        backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.makeInformationInvisible()
            }
        }
        onBackPressedDispatcher.addCallback(this, backCallback)

        isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    private fun setFragment() {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ItemListFragment())
                .commit()
    }

    private fun setShimmer() {
        lifecycleScope.launch {
            viewModel.isLoading.collect {
                if (viewModel.isLoading.value) {
                    binding.shimmerContainer.visibility = View.VISIBLE
                    binding.shimmerContainer.startShimmer()
                    delay(2000)
                } else {
                    binding.shimmerContainer.stopShimmer()
                    binding.shimmerContainer.visibility = View.GONE
                }
            }
        }

    }

    private fun observeViewModel() {
        viewModel.selectedItem.observe(this) { item ->
            item?.let { showInformationFragment(it) }
        }

        viewModel.informationFragmentVisibility.observe(this) {visibility ->
            if (visibility == false) {
                if (isPortrait) {
                    if (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStack()
                    } else {
                        finish()
                    }
                } else {
                    if (binding.informationFragmentContainer?.isVisible == true) {
                        binding.informationFragmentContainer!!.visibility = View.INVISIBLE
                    } else {
                        finish()
                    }
                }
            } else {
                if (binding.informationFragmentContainer?.isVisible == false) {
                    binding.informationFragmentContainer!!.visibility = View.VISIBLE
                }
            }
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
            lifecycleScope.launch {
                setFragment()
            }
            viewModel.selectedItem.value?.let {
                showInformationFragment(it)
            } ?: viewModel.isAddingNewItem.value?.takeIf { it }?.let {
                addNewItemFragment()
            }
        }
    }
}