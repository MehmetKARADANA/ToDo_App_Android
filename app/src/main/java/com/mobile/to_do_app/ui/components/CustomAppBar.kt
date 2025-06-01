package com.mobile.to_do_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String,
    showBackButton: Boolean,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    useGradientBackground: Boolean = false // Gradient kullanılıp kullanılmayacağını kontrol et
) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
            // Gradient'ın son rengi AppBar'ın normal arka planıyla uyumlu olabilir
            // veya tamamen farklı bir set olabilir.
        )
    )

    val appBarContainerColor = if (useGradientBackground) {
        Color.Transparent // Gradient kullanılacaksa AppBar'ın kendi rengi transparan olmalı
    } else {
        MaterialTheme.colorScheme.surface // Varsayılan yüzey rengi
    }

    Box(
        modifier = modifier.then(
            if (useGradientBackground) Modifier.background(brush = gradientBackground) else Modifier
        )
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface // Veya onPrimaryContainer eğer gradient'a uygunsa
                )
            },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = MaterialTheme.colorScheme.onSurface // Veya onPrimaryContainer
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = appBarContainerColor, // Dinamik olarak ayarlanır
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                // Eğer gradient kullanıyorsanız, buradaki titleContentColor ve navigationIconContentColor
                // gradient üzerindeki okunabilirliğe göre ayarlanmalıdır.
                // Örneğin: titleContentColor = if (useGradientBackground) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
            ),
            // modifier = Modifier.fillMaxWidth() // Box'a geçtiği için burada modifier'ı direkt kullanmayabiliriz veya Box'tan gelen modifier'ı alır.
            // scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }
}