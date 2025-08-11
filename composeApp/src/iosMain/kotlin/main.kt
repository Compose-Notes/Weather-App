import androidx.compose.ui.window.ComposeUIViewController
import dev.androidpoet.weatherapp.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
