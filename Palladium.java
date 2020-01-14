/*
File name:     Palladium.java
Description:   
*/

import java.io.*;
import java.util.Scanner;

public class Palladium {
	public static Scanner sc = new Scanner(System.in);
	final static int QUIT_KEY = 0;

	public static User currentUser;
	public static SubscriptionList subscriptionList = new SubscriptionList();
	public static CouponList couponList = new CouponList();
	public static GiftCardList giftCardList = new GiftCardList();
	public static CreditCardList creditCardList = new CreditCardList();

	public static void main(String[] args) {

		// starts the program, welcomeUi() will call all the other methods
		welcomeUi();
	}

	static String getStringInput() {
		String userInput;

		System.out.print("> ");
		userInput = sc.nextLine();

		return userInput;
	}

	static int getIntInput() {
		String userInputString;
		int userInputInt = 0;
		boolean validInput = false;

		do {
			System.out.print("> ");
			userInputString = sc.nextLine();

			try {
				userInputInt = Integer.parseInt(userInputString);
				validInput = true;
			} catch (Exception e) {
				System.out.println("\nInvalid input, please try again.\n");
			}
		} while (!validInput);

		return userInputInt;
	}

	static double getDoubleInput() {
		String userInputString;
		double userInputDouble = 0;
		boolean validInput = false;

		do {
			System.out.print("> ");
			userInputString = sc.nextLine();

			try {
				userInputDouble = Double.parseDouble(userInputString);
				validInput = true;
			} catch (Exception e) {
				System.out.println("\nInvalid input, please try again.\n");
			}
		} while (!validInput);

		return userInputDouble;
	}

	static DateTime getDateInput() {
		String userInputString;
		DateTime userInputDate = null;

		boolean validInput = false;

		do {
			System.out.print("> ");
			userInputString = sc.nextLine();

			try {
				userInputDate = new DateTime(userInputString);
				validInput = true;
			} catch (Exception e) {
				System.out.println("\nInvalid input, please try again.\n");
			}
		} while (!validInput);

		return userInputDate;
	}

	public static void welcomeUi() {
		System.out.println(" __________________________________________________");
		System.out.println("|                                                  |");
		System.out.println("| Welcome to Palladium                             |");
		System.out.println("| The financial toolkit                            |");
		System.out.println("|                                                  |");
		System.out.println("| Please select an option:                         |");
		System.out.println("| Login                                       (1)  |");
		System.out.println("| Signup                                      (2)  |");
		System.out.println("|__________________________________________________|");
		System.out.println("");

		int userWelcomeChoice = 0;
		while (userWelcomeChoice != 1 || userWelcomeChoice != 2) {

			userWelcomeChoice = Palladium.getIntInput();

			if (userWelcomeChoice == 1) {
				logInUi();
			} else if (userWelcomeChoice == 2) {
				signUpUi();
			}
		}
	}

	public static void logInUi() {
		Login login = new Login();
		String username, password, email, loginResult;

		do {
			System.out.println(" __________________________________________________");
			System.out.println("");
			System.out.println("Please enter your username: ");
			username = Palladium.getStringInput();
			System.out.println();
			System.out.println("Please enter your email:");
			email = Palladium.getStringInput();
			System.out.println();
			System.out.println("Please enter your password: ");
			password = Palladium.getStringInput();
			System.out.println(" __________________________________________________");

			loginResult = login.compareLogin(username, password);

			if (loginResult == "USER_NOT_FOUND") {
				System.out.println("");
				System.out.println(" __________________________________________________");
				System.out.println("|                                                  |");
				System.out.println("| Sorry but we cannot find your account.           |");
				System.out.println("|                                                  |");
				System.out.println("| Please double check your username and your       |");
				System.out.println("| password.                                        |");
				System.out.println("|__________________________________________________|");
				System.out.println("");
			}

		} while (loginResult == "USER_NOT_FOUND");

		Palladium.currentUser = new User(username, password, email);

		System.out.println(" ________________________________________");
		System.out.println("|                                        |");
		System.out.println("| Login is successful                    |");
		System.out.println("|________________________________________|");
		loadDataUi();
	}

	public static void signUpUi() {
		Login login = new Login();
		String username, password, email, signupResult;

		do {
			System.out.println(" __________________________________________________");
			System.out.println("");
			System.out.println("Please enter your username: ");
			username = Palladium.getStringInput();
			System.out.println();
			System.out.println("Please enter your email:");
			email = Palladium.getStringInput();
			System.out.println();
			System.out.println("Please enter your password: ");
			password = Palladium.getStringInput();
			System.out.println(" __________________________________________________");

			signupResult = login.register(username, password, email);

			if (signupResult.equals("WEAK_PASSWORD")) {
				System.out.println(" ________________________________________");
				System.out.println("|                                        |");
				System.out.println("| Weak Password!                         |");
				System.out.println("|                                        |");
				System.out.println("| Please ensure that your password has:  |");
				System.out.println("| - More than 8 characters               |");
				System.out.println("| - Uppercase and lowercase letter       |");
				System.out.println("| - At least 1 number                    |");
				System.out.println("| - At least 1 special character         |");
				System.out.println("|________________________________________|");
				System.out.println();
			}

			if (signupResult.equals("USERNAME_TAKEN")) {
				System.out.println(" ________________________________________");
				System.out.println("|                                        |");
				System.out.println("| Sorry, but this username has been      |");
				System.out.println("| taken, please select another one.      |");
				System.out.println("|________________________________________|");
				System.out.println();
			}

		} while (signupResult.equals("WEAK_PASSWORD") || signupResult.equals("USERNAME_TAKEN"));

		Palladium.currentUser = new User(username, password, email);
		System.out.println("You're now logged in as: " + username);

		// temp:
		loadDataUi();

	}

	public static void loadDataUi() {
		final String SUBSCRIPTION_LIST_FILE = "Database/" + Palladium.currentUser.getUsername()
				+ "/SubscriptionList.txt";

		boolean successfulLoad = subscriptionList.loadFromFile(SUBSCRIPTION_LIST_FILE);
		if (!successfulLoad) {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Couldn't load some data from the       |");
			System.out.println("| database, please make sure it is not   |");
			System.out.println("| corrupted.                             |");
			System.out.println("|________________________________________|");
			System.out.println();
		}

		mainMenuUi();
	}

	public static void mainMenuUi() {
		int userMenuChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Palladium Menu                         |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Quit Palladium                    (0)  |");
			System.out.println("| Accounts and Payments             (1)  |");
			System.out.println("| Shopping                          (2)  |");
			System.out.println("| Memberships and Subscriptions     (3)  |");
			System.out.println("| Coupons and Giftcards             (4)  |");
			System.out.println("| Notifications                     (5)  |");
			System.out.println("| Search                            (6)  |");
			System.out.println("|________________________________________|");
			System.out.println();

			userMenuChoice = Palladium.getIntInput();

			switch (userMenuChoice) {
			case 0:
				reaffirmQuit();
				break;
			case 1:
				// redirect to accounts and payments
				accountsAndPaymentsUi();
				break;
			case 2:
				// redirect to shopping
				shoppingUi();

				break;
			case 3:
				// redirect to Memberships and Subscriptions
				membershipsAndSubscriptionsUi();

				break;
			case 4:
				// coupons and giftcards
				couponsAndGiftCardsUi();
				break;
			case 5:
				// notifications
				notificationsUi();
				break;
			case 6:
				// search
				searchUi();
				break;
			}
		} while (userMenuChoice != 0);
	}

	public static void reaffirmQuit() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Are you sure you want to quit?         |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("|                                        |");
			System.out.println("| No                                 (0) |");
			System.out.println("| Yes                                (1) |");
			System.out.println("|________________________________________|");
			System.out.println();

			userChoice = Palladium.getIntInput();

			switch (userChoice) {
			case 0:
				break;
			case 1:
				System.exit(0);
				break;
			default:
				System.out.println("\nInvalid input.\n");
			}
		} while (userChoice != Palladium.QUIT_KEY);
	}

	public static void accountsAndPaymentsUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Accounts and Payments                  |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| Credit Cards                      (1)  |");
			System.out.println("| Website Accounts                  (2)  |");
			System.out.println("|________________________________________|");
			System.out.println();

			userChoice = Palladium.getIntInput();

			switch (userChoice) {
				case 0:
					// do nothing
				break;
				case 1:
					// credit cards
				break;
				case 2:
					// website accounts
				break;
			}

		} while (userChoice != Palladium.QUIT_KEY);
	}

	public static void creditCardUi() {
		int userChoice;

		do {

			
			userChoice = Palladium.getIntInput();

			switch (userChoice) {
				case 0:
				// do nothing
				break;
				case 1:
				//
				break;
			}
		} while (userChoice != 0);
	}

	public static void websiteAccountsUi() {
		int userChoice;
	}

	public static void shoppingUi() {
		int userShoppingChoice;
		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Shopping                               |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| Shopping Cart                     (1)  |");
			System.out.println("| Wishlist                          (2)  |");
			System.out.println("|________________________________________|");
			System.out.println();

			userShoppingChoice = Palladium.getIntInput();

			switch (userShoppingChoice) {
			case 1:
				// shopping cart
				shoppingCartUi();
				break;
			case 2:
				// wishlist
				wishlistUi();
				break;
			default:
				System.out.println("Invalid input.");
			}
		} while (userShoppingChoice != 0);
	}

	public static void shoppingCartUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Shopping Cart                          |");
			System.out.println("|                                        |");
			System.out.println("|                                        |");
			System.out.println("|________________________________________|");
			System.out.println();

			userChoice = Palladium.getIntInput();

		} while (userChoice != Palladium.QUIT_KEY);
	}

	public static void wishlistUi() {
		int userChoice;

	}

	public static void membershipsAndSubscriptionsUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Memberships and Subscriptions          |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| Memberships                       (1)  |");
			System.out.println("| Subscription                      (2)  |");
			System.out.println("|________________________________________|");
			System.out.println();

			userChoice = Palladium.getIntInput();

			switch (userChoice) {
			case 0:
				// do nothing
				break;
			case 1:
				membershipsUi();
				break;
			case 2:
				subscriptionsUi();
				break;
			default:
				System.out.println("Invalid input.");
			}

		} while (userChoice != Palladium.QUIT_KEY);
	}

	public static void membershipsUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Memberships                           |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| List all Memberships              (1)  |");
			System.out.println("| Add Membership                    (2)  |");
			System.out.println("| Remove a Membership               (3)  |");
			System.out.println("| Edit a Membership                 (4)  |");
			System.out.println("| View Membership Statistics        (5)  |");
			System.out.println("|________________________________________|");
			System.out.println();

			userChoice = Palladium.getIntInput();

			switch (userChoice) {
			case 0:
				// do nothing
				break;
			case 1:
				// list all memberships

				break;
			case 2:

				break;
			case 3:

				break;

			}

		} while (userChoice != 0);

	}

	public static void subscriptionsUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Subscriptions                          |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| List all Subscriptions            (1)  |");
			System.out.println("| Add Subscription                  (2)  |");
			System.out.println("| Remove a Subscription             (3)  |");
			System.out.println("| Edit a Subscription               (4)  |");
			System.out.println("| View Subscription Statistics      (5)  |");
			System.out.println("|________________________________________|");
			System.out.println();

			userChoice = Palladium.getIntInput();

			switch (userChoice) {
			case 0:
				// do nothing
				break;
			case 1:
				// view all subscriptions
				if (Palladium.subscriptionList.length == 0) {
					// empty subscription list
					System.out.println(" ________________________________________");
					System.out.println("|                                        |");
					System.out.println("| No Subscriptions Found                 |");
					System.out.println("|________________________________________|");
				}

				for (int i = 0; i < Palladium.subscriptionList.length; i++) {
					if (i == 0) {
						System.out.println("_________________________________________");
					}
					System.out.println("");
					System.out.println("  Subscription (" + Palladium.subscriptionList.getSubscriptionIndex(i).getName()
							+ ") #" + (i + 1));
					System.out.println("");
					System.out.println(
							"  Name:              " + Palladium.subscriptionList.getSubscriptionIndex(i).getName());
					System.out.println(
							"  Cost:              " + Palladium.subscriptionList.getSubscriptionIndex(i).getCost());
					System.out.println("  Purchased Date:    "
							+ Palladium.subscriptionList.getSubscriptionIndex(i).getPurchasedDate().toString());
					System.out.println("  Expiry Date:       "
							+ Palladium.subscriptionList.getSubscriptionIndex(i).getExpiryDate().toString());
					System.out.println("  Times Renewed:     "
							+ Palladium.subscriptionList.getSubscriptionIndex(i).getTimesRenewed());
					System.out.println("_________________________________________");
				}
				break;

			case 2:
				// add a subscription
				String name;
				double cost;
				DateTime purchasedDate, expiryDate;
				int timesRenewed;

				System.out.println(" ________________________________________");
				System.out.println("|                                        |");
				System.out.println("| Add a new subscription                 |");
				System.out.println("|________________________________________|");
				System.out.println("\nPlease enter your details below\n");

				System.out.println("Please enter the name:");
				name = Palladium.getStringInput();

				System.out.println("Please enter the cost of the subscription per month:");
				cost = Palladium.getDoubleInput();

				System.out.println("Please enter the date when you purchased the subscription:");
				purchasedDate = Palladium.getDateInput();

				System.out.println("Please enter when your subscription renews:");
				expiryDate = Palladium.getDateInput();

				System.out.println("Please enter how many times you renewed this subscription:");
				timesRenewed = Palladium.getIntInput();

				Subscription newSubscription = new Subscription(name, cost, purchasedDate, expiryDate, timesRenewed);

				if (Palladium.subscriptionList.addSubscription(newSubscription)) {
					System.out.println(" ________________________________________");
					System.out.println("|                                        |");
					System.out.println("| Subscription has been added            |");
					System.out.println("|________________________________________|");
					System.out.println();
				} else {
					System.out.println(" ________________________________________");
					System.out.println("|                                        |");
					System.out.println("| Error adding subscription, please      |");
					System.out.println("| try again.                             |");
					System.out.println("|________________________________________|");
					System.out.println();
				}
				break;

			case 3:
				int indexToRemove;
				boolean validRemove;

				System.out.println(" ________________________________________");
				System.out.println("|                                        |");
				System.out.println("| Remove a subscription                  |");
				System.out.println("|________________________________________|");
				System.out.println("");

				System.out.println("Please enter an index to remove:");

				indexToRemove = Palladium.getIntInput();

				indexToRemove--;
				validRemove = Palladium.subscriptionList.removeSubscription(indexToRemove);

				if (validRemove) {
					System.out.println(" ________________________________________");
					System.out.println("|                                        |");
					System.out.println("| Successful removal of subscription     |");
					System.out.println("|________________________________________|");
				} else {
					System.out.println(" ________________________________________");
					System.out.println("|                                        |");
					System.out.println("| Removal failed, please try again.      |");
					System.out.println("| Please check the index you entered.    |");
					System.out.println("|________________________________________|");
				}

				break;
			}

		} while (userChoice != Palladium.QUIT_KEY);
	}

	public static void couponsAndGiftCardsUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Coupons and Gift Cards                 |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| Coupons                           (1)  |");
			System.out.println("| Gift Cards                        (2)  |");
			System.out.println("|________________________________________|");
			System.out.println("");

			userChoice = Palladium.getIntInput();

			switch (userChoice) {
			case 0:
				// does nothing
				break;
			case 1:
				couponsUi();
				break;
			case 2:
				giftCardUi();
				break;
			default:
			}

		} while (userChoice != Palladium.QUIT_KEY);

	}

	public static void couponsUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Coupons                                |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| Display All Coupons               (1)  |");
			System.out.println("| Add Coupon                        (2)  |");
			System.out.println("| Remove a Coupon                   (3)  |");
			System.out.println("|________________________________________|");
			System.out.println("");

			userChoice = Palladium.getIntInput();

			switch (userChoice) {
			case 0:
				// do nothing
				break;
			case 1:
				// list all coupons
				if (Palladium.couponList.length == 0) {
					// empty subscription list
					System.out.println(" ________________________________________");
					System.out.println("|                                        |");
					System.out.println("| No Coupons Found                       |");
					System.out.println("|________________________________________|");
				}

				for (int i = 0; i < Palladium.couponList.length; i++) {
					if (i == 0) {
						System.out.println("_________________________________________");
					}
					System.out.println("");
					System.out
							.println("  Coupon (" + Palladium.couponList.getCouponIndex(i).getName() + ") #" + (i + 1));
					System.out.println("");
					System.out.println("  Name:          " + Palladium.couponList.getCouponIndex(i).getName());
					System.out.println("  Retailer:      " + Palladium.couponList.getCouponIndex(i).getRetailer());
					System.out.println("  Discount:      " + Palladium.couponList.getCouponIndex(i).getDiscount());
					System.out.println(
							"  Expiry Date:   " + Palladium.couponList.getCouponIndex(i).getExpiryDate().toString());
					System.out.println("_________________________________________");
				}
				break;
			case 2:
				// add a coupon
				Coupon newCoupon;

				String name, retailer;
				double discount;
				DateTime expiryDate;

				System.out.println(" ________________________________________");
				System.out.println("|                                        |");
				System.out.println("| Add a new coupon                       |");
				System.out.println("|________________________________________|");
				System.out.println("\nPlease enter your details below\n");

				System.out.println("Please enter a name for your coupon:");
				name = Palladium.getStringInput();

				System.out.println("Please enter the retailer of your coupon:");
				retailer = Palladium.getStringInput();

				System.out.println("Please enter the monetary discount of your coupon:");
				discount = Palladium.getDoubleInput();

				System.out.println("Please enter the date when the coupon expires:");
				expiryDate = Palladium.getDateInput();

				newCoupon = new Coupon(name, retailer, discount, expiryDate);

				if (Palladium.couponList.addCoupon(newCoupon)) {
					System.out.println(" ________________________________________");
					System.out.println("|                                        |");
					System.out.println("| Coupon has been added                  |");
					System.out.println("|________________________________________|");
					System.out.println();
				} else {
					System.out.println(" ________________________________________");
					System.out.println("|                                        |");
					System.out.println("| Error, coupon could not be added.      |");
					System.out.println("| Please try again.                      |");
					System.out.println("|________________________________________|");
					System.out.println();
				}

				break;
			}

		} while (userChoice != Palladium.QUIT_KEY);
	}

	public static void giftCardUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Gift Cards                             |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| Display all Credit Cards          (1)  |");
			System.out.println("| Add a Credit Card                 (2)  |");
			System.out.println("| Remove a Credit Card              (3)  |");
			System.out.println("|________________________________________|");
			System.out.println("");

			userChoice = Palladium.getIntInput();
		} while (userChoice != Palladium.QUIT_KEY);

		switch (userChoice) {
		case 0:
			// do nothing
			break;
		case 1:
			// display all credit cards
			if (Palladium.couponList.length == 0) {
				// empty subscription list
				System.out.println(" ________________________________________");
				System.out.println("|                                        |");
				System.out.println("| No Gift Cards Found                    |");
				System.out.println("|________________________________________|");
			}

			for (int i = 0; i < Palladium.couponList.length; i++) {
				if (i == 0) {
					System.out.println("_________________________________________");
				}
				System.out.println("");
				System.out.println("  Gift Card (" + Palladium.couponList.getCouponIndex(i).getName() + ") #" + (i + 1));
				System.out.println("");
				System.out.println("  Name:          " + Palladium.couponList.getCouponIndex(i).getName());
				System.out.println("  Retailer:      " + Palladium.couponList.getCouponIndex(i).getRetailer());
				System.out.println("  Discount:      " + Palladium.couponList.getCouponIndex(i).getDiscount());
				System.out.println(
						"  Expiry Date:   " + Palladium.couponList.getCouponIndex(i).getExpiryDate().toString());
				System.out.println("_________________________________________");
			}
			
			break;
		case 2:
			// add a credit card
			break;
		case 3:
			// remove a credit card
		}

	}

	public static void notificationsUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Notifications                          |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| Display All                       (1)  |");
			System.out.println("|________________________________________|");
			System.out.println();

			userChoice = Palladium.getIntInput();

			switch (userChoice) {
			case 0:
				break;
			case 1:
				// display all notifications
				break;
			}
		} while (userChoice != Palladium.QUIT_KEY);
	}

	public static void searchUi() {
		int userChoice;

		do {
			System.out.println(" ________________________________________");
			System.out.println("|                                        |");
			System.out.println("| Search                                 |");
			System.out.println("|                                        |");
			System.out.println("| Please select an option:               |");
			System.out.println("|________________________________________|");
			System.out.println("| Go Back                           (0)  |");
			System.out.println("| I don't know                      (1)  |");
			System.out.println("|________________________________________|");
			System.out.println();

			userChoice = Palladium.getIntInput();

			switch (userChoice) {
			case 0:
				break;
			case 1:
				break;
			}
		} while (userChoice != Palladium.QUIT_KEY);

	}

}