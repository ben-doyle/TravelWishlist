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
        staticFileLocation("/public");
        WishListDAO dao = new SimpleWishListDao();

        before(((request, response) -> {
            if(request.cookie("username") != null) {
                request.attribute("username",
                        request.cookie("username"));
            }
        }));

        before("/locations", (req, res) -> {
            if (req.attribute("username") == null) {
                setFlashMessage(req, "Whoops, please sign in first!");
                res.redirect("/");
                halt();
            }
        });

        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", req.attribute("username"));
            model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model,"index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sign-in", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.queryParams("username");
            res.cookie("username", username);
            model.put("username", username);
            res.redirect("/locations");
            return null;
        });

        get("/locations", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("locations", dao.findAll());
            model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model, "locations.hbs");
        }, new HandlebarsTemplateEngine());

        post("/locations", (req, res) -> {
            String name = req.queryParams("name");
            WishListIdea locationIdea = new WishListIdea(name, req.attribute("username"));
            dao.add(locationIdea);
            res.redirect("/locations");
            return null;
        });

        get("/location/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("locationIdea", dao.findBySlug(req.params("slug")));
            return new ModelAndView(model, "location.hbs");
        }, new HandlebarsTemplateEngine());

        post("/location/:slug/vote", (req, res) -> {
            WishListIdea locationIdea = dao.findBySlug(req.params("slug"));
            boolean added = locationIdea.addVoter(req.attribute("username"));
            if (added) {
                setFlashMessage(req, "Thanks for your vote!");
            } else {
                setFlashMessage(req, "You already voted!");
            }
            res.redirect("/locations");
            return null;
        });

        exception(NotFoundException.class, (exc, req, res) -> {
            res.status(404);
            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
            String html = engine.render(
                    new ModelAndView(null, "not-found.hbs"));
            res.body(html);
        });
    }

    private static void setFlashMessage(Request req, String message) {
        req.session().attribute(FLASH_MESSAGE_KEY, message);
    }

    private static String getFlashMessage(Request req) {
        if (req.session(false) == null) {
            return null;
        }
        if (!req.session().attributes().contains(FLASH_MESSAGE_KEY)) {
            return null;
        }
        return (String) req.session().attribute(FLASH_MESSAGE_KEY);
    }

    private static String captureFlashMessage(Request req) {
        String message = getFlashMessage(req);
        if (message != null) {
            req.session().removeAttribute(FLASH_MESSAGE_KEY);

        }
        return message;
    }
}
