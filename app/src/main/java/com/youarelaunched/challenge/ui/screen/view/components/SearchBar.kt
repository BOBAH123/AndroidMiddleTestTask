package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    performSearch: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        elevation = 18.dp,
        color = VendorAppTheme.colors.background,
        shape = RoundedCornerShape(size = 16.dp),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = searchText,
            onValueChange = onSearchTextChange,
            placeholder = {
                Text(
                    text = "Search...",
                    color = VendorAppTheme.colors.text,
                    style = VendorAppTheme.typography.subtitle2
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = VendorAppTheme.colors.text,
                disabledTextColor = Color.Transparent,
                backgroundColor = VendorAppTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = performSearch) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_search),
                        tint = VendorAppTheme.colors.text,
                        contentDescription = "Search Button"
                    )
                }
            }
        )
    }
}