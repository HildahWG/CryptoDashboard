

# 📱 CryptoDashboard

**CryptoDashboard** is a sleek and responsive Android app built using **Kotlin** and **Jetpack Compose**. It allows users to view **real-time cryptocurrency data**, including:

* ✅ Coin names, symbols, and current prices
* ✅ 1-hour percentage changes (color-coded)
* ✅ Line charts showing 7-day trends
* ✅ Detailed coin information on a separate screen
* ✅ Buy and Sell action buttons for trading simulation

> This app is ideal for users who want to stay updated on market performance in a visually intuitive interface.

---

## 🧠 New Features Added (June 2025) — by [WECK-web](https://github.com/WECK-web)

As part of the team collaboration task, the following features were added:

### 🔐 Firebase Authentication

* Users can **Sign Up** and **Log In** securely using Firebase.
* Only logged-in users can access the main dashboard.
* Login state is managed using ViewModel + Navigation.

### ⭐ Watchlist Functionality

* Users can **favorite coins** using the ⭐ icon on the Dashboard.
* A new **Watchlist screen** displays only the favorited coins.
* All favorites are stored and synced in **Firebase Firestore** in real-time.

---

## 🧰 Technologies & Tools Used

| Feature          | Tech Stack                   |
| ---------------- | ---------------------------- |
| UI Framework     | Jetpack Compose              |
| State Management | MVVM + ViewModels            |
| API Integration  | Retrofit + Kotlin Coroutines |
| Backend Auth     | Firebase Authentication      |
| Cloud Storage    | Firebase Firestore           |
| Navigation       | Jetpack Navigation Compose   |

---

## 🧪 Setup Instructions (Build + Run)

To run the app locally:

1. **Clone** the repository
2. Open in **Android Studio (Flamingo or newer)**
3. Let Gradle finish syncing
4. Build the project (`Build > Make Project` or press `Ctrl + F9`)
5. Run the app on a physical device or emulator

📱 Minimum SDK: **24**
☁️ Ensure internet connection to allow data fetching from CoinGecko & Firebase

---

## 🌐 API Usage Overview (CoinGecko)

### Endpoints used:

* `/coins/markets` – Fetches coin prices and sparkline data
* `/coins/{id}` – Detailed coin info

### Data retrieved:

* Coin name, symbol, image
* Current price and market cap
* 1-hour price change percentage
* 7-day sparkline data for charts

---

## 📸 App Layout (Light Mode)

![image](https://github.com/user-attachments/assets/f3a7b9b9-5d62-4008-bdff-b760a9e9b394)
![image](https://github.com/user-attachments/assets/20bb3631-58a9-47bc-a808-1b5aec6c92ce)
![image](https://github.com/user-attachments/assets/171c818a-e599-4c46-870e-ac074439feb2)

---

## 🌙 App Layout (Dark Mode)

![image](https://github.com/user-attachments/assets/19031847-3e1a-4c0c-94c0-087151169d82)
![image](https://github.com/user-attachments/assets/892813b0-3ac6-42c6-bb1d-80f24bc9e9dd)
![image](https://github.com/user-attachments/assets/6d5fc45e-c29d-4bce-83c2-f90418bbb51b)

---

## 🎥 Demo Video

* [https://github.com/user-attachments/assets/7fc4446a-1c21-43e5-ac27-3b452215ad0](https://github.com/user-attachments/assets/7fc4446a-1c21-43e5-ac27-3b452215ad0)
* [https://github.com/user-attachments/assets/6081e1da-daf8-4356-841a-97c152feaf36](https://github.com/user-attachments/assets/6081e1da-daf8-4356-841a-97c152feaf36)

---

## 👥 Contributors

| Name         | GitHub Username                         | Contributions                        |
| ------------ | --------------------------------------- | ------------------------------------ |
| Original Dev | HildahWG                                | Coin data, Compose UI, Dashboard     |
| Contributor  | [WECK-web](https://github.com/WECK-web) | Firebase Auth, Watchlist, Navigation |

---

