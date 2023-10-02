package com.sampleapp.sampleremotemediator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.sampleapp.sampleremotemediator.ui.presenter.MessagesViewModel
import com.sampleapp.sampleremotemediator.ui.theme.SampleRemoteMediatorTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleRemoteMediatorTheme {

                val messagesViewModel = hiltViewModel<MessagesViewModel>()

                val messages = messagesViewModel.returnMessagesPagingSource().collectAsLazyPagingItems()


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ){

                 items(count = messages.itemCount){index ->

                     Spacer(modifier = Modifier.height(20.dp))

                     Text(modifier = Modifier.fillMaxWidth(), text = messages[index]?.message.toString(), fontSize = 20.sp, textAlign = TextAlign.Center)

                     Spacer(modifier = Modifier.height(20.dp))

                    }
                }
            }
        }
    }
}

