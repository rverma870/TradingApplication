# Trading-Application-System

### About Application

Created a application where you can see live prices of stocks. And featured with options to buy and sell for a particular stock.

- For successfull order, Last Transaction Price from live data should be equal to market ordered file.
- For successfull sell, entered volume should be less than or equal to available stock volume of user.

### Description

- Designed in MVC model.
- Developed Data Loader to get live price.
- Pushed events by message broker to web-socket over STOMP protocol for all subscribers of that topic.
- Developed Rest APIs end points.
- Created Async operation in new thread for Buy and Sell requests in multi threaded environment.
- Track User transaction information with the help of HashMap.
- Export .log file for every order request (buy/sell).

### Technologies/Framework Used

- Java
- Spring Boot
- Java Script
- React
- HTML CSS
- Bootstrap

### Versions

- node - 16.18.0
- git - 2.25.1
- npm - 8.19.2
- java - 17.0.2
