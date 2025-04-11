import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.health.R
import com.example.health.data.local.entities.DietDish
import com.example.health.data.local.viewmodel.DietDishViewModel
import com.example.health.screens.main.plan.vegan.HeaderWithBackButton
import kotlinx.coroutines.flow.Flow

@Composable
fun VeganDetailsScreen(
    navController: NavController,
    viewModel: DietDishViewModel,
    mealPlanId: String
) {
    val context = LocalContext.current

    LaunchedEffect(mealPlanId) {
        viewModel.syncIfNeeded(context)
        viewModel.loadByMealId(mealPlanId)
    }

    val dishes by viewModel.dishes.collectAsState()

    LaunchedEffect(dishes) {
        Log.d("VeganDetailScreen", "Dishes size = ${dishes.size}")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderWithBackButton(
            title = "Your details diet dish",
            onBackClick = { navController.popBackStack() }
        )

        LazyColumn(modifier = Modifier.padding(12.dp)) {
            items(dishes) { dish ->
                DietDishCard(dish = dish)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
@Composable
fun NutritionTagFixedWidth(iconRes: Int, label: String, value: Float, backgroundColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(120.dp) // ✅ cố định chiều rộng
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$label: ${value.toInt()}",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun DietDishCard(dish: DietDish) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F6FA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ảnh đại diện
            AsyncImage(
                model = dish.UrlImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tên món ăn
            Text(
                text = dish.Name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3741A0)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Các thông số dinh dưỡng chia 2 hàng - 2 cột
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NutritionTagFixedWidth(R.drawable.calo, "Calo", dish.Calo, Color(0xFFFFE0B2))
                    NutritionTagFixedWidth(R.drawable.carb, "Carb", dish.Carb, Color(0xFFBBDEFB))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NutritionTagFixedWidth(R.drawable.pro, "Protein", dish.Protein, Color(0xFFC8E6C9))
                    NutritionTagFixedWidth(R.drawable.fat, "Fat", dish.Fat, Color(0xFFFFCDD2))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Quantity: ${dish.Quantity} ${dish.QuantityType}",
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            )
        }
    }
}

