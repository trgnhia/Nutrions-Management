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
fun AgeItem(age: Int  , onValueChange: (Int) -> Unit) {
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
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "How old are you?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your age determines how much energy you should consume in a day.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)
            ) {
                NumberPicker(
                    value = age,
                    onValueChange = onValueChange,
                    range = 10..100,
                    dividersColor = Color.Black,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth(0.2f)
                )

                Text(
                    text = "years", // giống như "cm" bên height
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAgeItem() {
    var age by remember { mutableStateOf(18) }
    AgeItem(age = age, onValueChange = { age = it })
}
