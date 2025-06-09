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


LAYOUT OF THE APPLICATION IN LIGHT THEME
![image](https://github.com/user-attachments/assets/f3a7b9b9-5d62-4008-bdff-b760a9e9b394)
![image](https://github.com/user-attachments/assets/20bb3631-58a9-47bc-a808-1b5aec6c92ce)
![image](https://github.com/user-attachments/assets/171c818a-e599-4c46-870e-ac074439feb2)





LAYOUT OF THE APP IN DARK RHEME
![image](https://github.com/user-attachments/assets/19031847-3e1a-4c0c-94c0-087151169d82)
![image](https://github.com/user-attachments/assets/804ab047-ae7e-4d08-a2d8-56b52d3f2960)
![image](https://github.com/user-attachments/assets/6d5fc45e-c29d-4bce-83c2-f90418bbb51b)



A SHORT VIDEO DEMO OF THE LAYOUT OF THE APP
https://github.com/user-attachments/assets/6081e1da-daf8-4356-841a-97c152feaf36



