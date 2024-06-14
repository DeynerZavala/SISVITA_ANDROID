import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sisvita_android.R
import com.example.sisvita_android.navigation.AppNavigation
import com.example.sisvita_android.navigation.AppScreen
import com.example.sisvita_android.ui.theme.backgroundDark
import com.example.sisvita_android.ui.viewmodel.LoginViewModel
import com.example.sisvita_android.utils.UserManager

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MENÚ",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor =
                            MaterialTheme.colorScheme.primary)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.sisvita_logo),
                contentDescription = "Profile Icon",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            InformationCard()
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate(AppScreen.testHome.route) }) {
                Text("Realizar Test")
            }
            Button(onClick = { /* TODO: Handle click */ }) {
                Text("Consultar Resultado")
            }
            Button(onClick = { /* TODO: Handle click */ }) {
                Text("Recomendaciones")
            }
            Button(onClick = { /* TODO: Handle click */ }) {
                Text("Foro de Discusión")
            }
        }
    }
}
@Composable
fun InformationCard( loginViewModel: LoginViewModel = viewModel()) {
    val email: String by loginViewModel.correo.observeAsState("")
    val user = UserManager?.getUser()
    if(user !=null){
        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor =  MaterialTheme.colorScheme.primary,
            )
        ) {
            Text(
                text = "Información",
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Nombre : " + user.nombre+
                            "\nApellidos : "+user.apellido_paterno + " " + user.apellido_materno+
                            "\nCorreo : "+user.correo_electronico,
                    color = Color.White
                )
            }
        }
    }


}