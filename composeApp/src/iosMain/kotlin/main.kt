import androidx.compose.ui.window.ComposeUIViewController
import dev.androidpoet.weatherapp.App
import dev.androidpoet.weatherapp.OnboardingScreen
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
