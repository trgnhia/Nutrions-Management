import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.health.R

@Composable
fun AgeItem(age: Int, onValueChange: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Ảnh nền
        Image(
            painter = painterResource(id = R.drawable.agebackground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Tiêu đề
            Text(
                text = "How old are you?",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mô tả
            Text(
                text = "Your age determines how much energy you should consume in a day.",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Number Picker dạng bánh xe
            NumberPicker(
                value = age,
                onValueChange = onValueChange,
                range = 10..100,
                dividersColor = Color.Black,
                textStyle = LocalTextStyle.current.copy(color = Color.Black) // màu chữ trong picker
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAgeItem() {
    var age by remember { mutableStateOf(18) }
    AgeItem(age = age, onValueChange = { age = it })
}
