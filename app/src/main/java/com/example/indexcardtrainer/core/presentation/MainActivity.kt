package com.example.indexcardtrainer.core.presentation

import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import com.example.indexcardtrainer.core.presentation.navigation.NavigationHost
import com.example.indexcardtrainer.ui.theme.IndexCardTrainerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = hiltViewModel()
            IndexCardTrainerTheme {
                val navController = rememberNavController()
                viewModel.navController = navController
                viewModel.setUserRepoContext(this)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    /*
                    bottomBar = {
                        BreadCrumbNavigation(
                            currentDestination = viewModel.currentDestination.value,
                            onNavigationEvent = viewModel::onNavigationEvent,
                        )
                    }

                     */
                ) {
                    NavigationHost(navController = navController, modifier = Modifier.padding(bottom = it.calculateBottomPadding()))
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        val x =  super.getOnBackInvokedDispatcher()
        x.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
            viewModel.onNavigationEvent(NavigationEvent.OnBackClick)
        }
        return x
    }
}
