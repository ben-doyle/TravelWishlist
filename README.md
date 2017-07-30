# Travel Wish List

Google maps API-KEY AIzaSyDNb9_4ZFwEFfovzr899TZXB16BtPS_xyo

## RUNNING:

- Run `mvn clean install`
- Run `mvn exec:exec`
- spark will start on port 8080, accessible via `http://localhost:8080/`

## Design

- Using maven as a build tool
- Using Spark for the web handling
- Using Spark-Handlebars for templating 
- Display using google maps javascript api
- Maps functionality using google maps java api
- Using slugify for URI's
- It runs a DAO, so has no connection to a database.

### Things to improve on:
- Add database connection to keep data persistent.
- Make the CSS nicer and more mobile friendly.

---

# Task Description
### TRAVEL WISH LISTCreate an interface that allows users to pin-point locations throughout the world indicating where they would like to travel; building their own travel wish list!
Each location should have:

- [x] a description 
- [x] a count of votes
- [x] Alocation should be visible and be able to be voted on, by other users of the app.- [x] Once a location has received five unique votes the location should be highlighted as popular. 
- [ ] Users should be able to browse all locations by most votes and distance from their current location.#### Bonus ideas
- [ ] Merge locations automatically that are within 10 kilometres from each other- [ ] Browse locations by most popular country- [ ] Notify users who have voted for a location once it becomes popular