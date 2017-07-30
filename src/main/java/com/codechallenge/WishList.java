package com.codechallenge;

import com.codechallenge.model.*;

import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class WishList {

    private static final String FLASH_MESSAGE_KEY = "flash_message";

    public static void main(String[] args) {

        // Spark will run on port 8080
        port(8080);

        // Set static location for css/js
        staticFileLocation("/public");

        // Setup the dao for data storage
        LocationDAO dao = new SimpleLocationDao();

        before(((request, response) -> {
            if(request.cookie("username") != null) {
                request.attribute("username",
                        request.cookie("username"));
            }
        }));

        // If user tries to access locations before login, throw an error
        before("/locations", (req, res) -> {
            if (req.attribute("username") == null) {
                setFlashMessage(req, "Whoops, please sign in first!");
                res.redirect("/");
                halt();
            }
        });

        // If user tries to access wishlist before login, throw an error
        before("/wishlist", (req, res) -> {
            if (req.attribute("username") == null) {
                setFlashMessage(req, "Whoops, please sign in first!");
                res.redirect("/");
                halt();
            }
        });

        // Main path for wishlist
        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", req.attribute("username"));
            model.put("flashMessage", captureFlashMessage(req));
            model.put("pins", dao.getLocations());
            return new ModelAndView(model,"index.hbs");
        }, new HandlebarsTemplateEngine());

        // Clear the cookie to logout
        get("/logout", (req, res) -> {
            res.cookie("username", "");
            res.redirect("/");
            return null;
        });

        //Post method to sign in
        post("/sign-in", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.queryParams("username");
            res.cookie("username", username);
            model.put("username", username);
            res.redirect("/");
            return null;
        });

        //List of all locatations
        get("/locations", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("locations", dao.findAll());
            model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model, "locations.hbs");
        }, new HandlebarsTemplateEngine());

        // post for locations page for new location
        post("/locations", (req, res) -> {
            String name = req.queryParams("name");
            String description = req.queryParams("description");
            Location locationIdea = new Location(name, req.attribute("username"), description);
            setFlashMessage(req, name + " has been added!");
            dao.add(locationIdea);
            res.redirect("/locations");
            return null;
        });

        // post for wishlist page for new location
        post("/wishlist", (req, res) -> {
            String name = req.queryParams("name");
            String description = req.queryParams("description");
            Location locationIdea = new Location(name, req.attribute("username"), description);
            setFlashMessage(req, name + " has been added!");
            dao.add(locationIdea);
            res.redirect("/wishlist");
            return null;
        });

        // URI for each location
        get("/location/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("locationIdea", dao.findBySlug(req.params("slug")));
            return new ModelAndView(model, "location.hbs");
        }, new HandlebarsTemplateEngine());

        // wishlist/page of users current votes
        get("/wishlist", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("locations", dao.findUserLocations(req.attribute("username")));
            model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model, "wishlist.hbs");
        }, new HandlebarsTemplateEngine());

        // post/vote page
        post("/location/:slug/vote", (req, res) -> {
            Location locationIdea = dao.findBySlug(req.params("slug"));
            boolean added = locationIdea.addVoter(req.attribute("username"));
            if (added) {
                setFlashMessage(req, "Thanks for your vote!");
            } else {
                setFlashMessage(req, "You already voted!");
            }
            res.redirect("/locations");
            return null;
        });

        //Throw up a 404 if doesn't match current get.
        exception(NotFoundException.class, (exc, req, res) -> {
            res.status(404);
            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
            String html = engine.render(
                    new ModelAndView(null, "not-found.hbs"));
            res.body(html);
        });
    }

    /**
     *  Create the flash message cookie.
     */
    private static void setFlashMessage(Request req, String message) {
        req.session().attribute(FLASH_MESSAGE_KEY, message);
    }

    /**
     *  Get the flash message from the cookie.
     */
    private static String getFlashMessage(Request req) {
        if (req.session(false) == null) {
            return null;
        }
        if (!req.session().attributes().contains(FLASH_MESSAGE_KEY)) {
            return null;
        }
        return (String) req.session().attribute(FLASH_MESSAGE_KEY);
    }

    /**
     *  Capture flash message for use.
     */
    private static String captureFlashMessage(Request req) {
        String message = getFlashMessage(req);
        if (message != null) {
            req.session().removeAttribute(FLASH_MESSAGE_KEY);

        }
        return message;
    }
}
