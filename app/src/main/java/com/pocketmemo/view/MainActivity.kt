package com.pocketmemo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.pocketmemo.R
import com.pocketmemo.data.AppDatabase
import com.pocketmemo.databinding.ActivityMainBinding
import com.pocketmemo.repository.RepositoryMemo
import com.pocketmemo.viewmodel.ViewModelMemo

class MainActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener
{
    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private lateinit var viewModel: ViewModelMemo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setSupportActionBar(binding.toolbar)
        //supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.run {
            findNavController(R.id.nav_host_fragment)
                .addOnDestinationChangedListener(this@MainActivity)
        }

        binding.fab.apply {
            setOnClickListener {
                setShowMotionSpecResource(R.animator.fab_show)
                setHideMotionSpecResource(R.animator.fab_hide)
                navigateToEdit()
            }
        }

        initViewModel()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    // INTERFACE /////////////////////

    // NavController.OnDestinationChangedListener [---

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.MemoListFragment -> {
                binding.fab.show()
            }
            R.id.MemoEditFragment -> {
                binding.fab.hide()
            }
        }
    }

    // NavController.OnDestinationChangedListener ]---

    //////////////////////////////////

    private fun initViewModel() {
        val repo = RepositoryMemo.getInstance(AppDatabase.getInstance(this).memoDao())
        //val vm: ViewModelMemo by viewModels()
        viewModel = ViewModelProvider(this, ViewModelMemo.Factory(repo)).get(ViewModelMemo::class.java)
    }

    private fun navigateToEdit() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
        }

        viewModel.selected(null)

        findNavController(R.id.nav_host_fragment)
            .navigate(MemoEditFragmentDirections.actionGlobalEdit())
    }
}