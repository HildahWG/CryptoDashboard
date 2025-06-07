CryptoDashboard is a sleek and responsive Android app built using Kotlin and Jetpack Compose. It allows users to view real-time cryptocurrency data, including:
Coin names, symbols, and current prices
1-hour percentage changes (color-coded)
Line charts showing 7-day trends
Detailed coin information on a separate screen
Buy and Sell action buttons for trading simulation
This app is ideal for users who want to stay updated on the market performance of various coins in a visually intuitive interface.

Setup Instructions (Build + Run)
To set up and run the CryptoDashboard app locally:
  1.Clone the repository
  2.Open in Android Studio
  3.Build the project
  4.Let Gradle finish syncing.
Build the project using Build > Make Project or press Ctrl + F9.
  5.Run the app
  Minimum SDK: 24
Tools Required: Android Studio Flamingo or newer, Kotlin, Gradle

 API Usage Overview
This app uses the CoinGecko API to fetch cryptocurrency market data.
Endpoints used:
/coins/markets: Retrieves a list of coins with current prices, volume, and sparkline data.
/coins/{id}: Fetches detailed information about a specific coin.
sparkline=true and price_change_percentage=1h parameters are used to enhance data display with trends and price changes.

Data retrieved:
Coin name, symbol, image
Current price and market cap
1-hour price change percentage
7-day sparkline data for line charts

