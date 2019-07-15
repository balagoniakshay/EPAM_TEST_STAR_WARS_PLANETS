package com.epam.tests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlanetsAPITest {

    @Test
    public void testGetAllPlanets(){

        RestAssured.baseURI = "http://swapi.co/api";

        RequestSpecification request = RestAssured.given();

        Response response = request.request(Method.GET, "/planets");

        Assert.assertEquals(response.getStatusCode(), 200, "Response Should have status code 200");

        Assert.assertTrue(response.getStatusLine().contains("OK"), "Response Should have status line OK");

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Response content should be ");

        Assert.assertTrue(response.getHeader("Set-Cookie")!= null, "Response should have set with cookie");

        Assert.assertEquals(response.getHeader("Allow"), "GET, HEAD, OPTIONS", "Response should allow GET, HEAD, OPTIONS");

        JsonPath js = response.jsonPath();

        Assert.assertTrue(js.getInt("count") > 0 , "Total count of planets should be greater than zero");

        Assert.assertTrue(js.getString("results") != null , "Response should have details for all the planets in the results section");

        Assert.assertTrue(js.getString("results[0].name") != null , "Response should have planet name");

        Assert.assertTrue(js.getString("results[0].residents[1]") != null , "Response should have residents");

        Assert.assertTrue(js.getString("results[0].films")!= null , "Response should have films");

        Assert.assertTrue(js.getString("results[0].created") != null , "Response should have timestamp for creation");

        Assert.assertTrue(js.getString("results[0].edited") != null , "Response should have timestamp of updated");

        Assert.assertTrue(js.getString("results[0].url") != null , "Response should have url of the planet with its id");

        Assert.assertEquals(js.getInt("count"), 61, "The count of all the planet is 61");

        Assert.assertEquals(js.getString("results[0].name") , "Alderaan", "Response should have planet name : Alderaan");

        Assert.assertEquals(js.getString("results[0].climate") , "temperate", "Response should have climate is temperate");

        Assert.assertEquals(js.getString("results[1].rotation_period") , "24", "The rotation period for the planet Yavin IV is 24");

        Assert.assertEquals(js.getString("results[2].orbital_period") , "549", "The orbital period for the planet Hoth is 549");

        Assert.assertEquals(js.getString("results[3].diameter") , "8900", "The diameter for the planet Dagobah is 8900");

        Assert.assertEquals(js.getString("results[4].gravity") , "1.5 (surface), 1 standard (Cloud City)", "The gravity for the planet Bespin is 1.5 (surface), 1 standard (Cloud City)");

        Assert.assertEquals(js.getString("results[5].terrain") , "forests, mountains, lakes", "The terrain for the planet Endor is forests, mountains, lakes");

        Assert.assertEquals(js.getString("results[6].surface_water") , "12", "The surface_water for the planet Naboo is 12");

        Assert.assertEquals(js.getString("results[7].population") , "1000000000000", "The surface_water for the planet Coruscant is 1000000000000");

        Assert.assertTrue(response.getTime() < 5000 , "Response time should be less than 5 seconds ");
    }

    @Test
    public void testGetPlanetById(){

        RestAssured.baseURI = "http://swapi.co/api";

        RequestSpecification request = RestAssured.given();

        Response response = request.request(Method.GET, "/planets/" + "2");

        Assert.assertEquals(response.getStatusCode(), 200, "Response Should have status code 200");

        Assert.assertTrue(response.getStatusLine().contains("OK"), "Response Should have status line OK");

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Response content should be application/json");

        Assert.assertTrue(response.getHeader("Set-Cookie")!= null, "Response should have set with cookie");

        Assert.assertEquals(response.getHeader("Allow"), "GET, HEAD, OPTIONS", "Response should allow GET, HEAD, OPTIONS");

        JsonPath js = response.jsonPath();

        Assert.assertTrue(js.getString("name")!=null, "Planet should have name of Type String");

        Assert.assertTrue(response.getTime() < 3000 , "Response time should be less than 3 seconds ");

        Assert.assertTrue(js.getString("residents[1]") != null , "Response should have residents");

        Assert.assertTrue(js.getString("created") != null , "Response should have timestamp for creation");

        Assert.assertTrue(js.getString("edited") != null , "Response should have timestamp of updated");

        Assert.assertTrue(js.getString("url") != null , "Response should have url of the planet with its id");

        Assert.assertEquals(js.getString("name") , "Alderaan", "The name of the of the planet is Alderaan");

        Assert.assertEquals(js.getString("climate") , "temperate", "The climate of the planet is temperate");

        Assert.assertEquals(js.getString("rotation_period") , "24", "The rotation period of the planet is 24");

        Assert.assertEquals(js.getString("orbital_period") , "364", "The orbital period of the planet is 549");

        Assert.assertEquals(js.getString("diameter") , "12500", "The diameter of the planet is 8900");

        Assert.assertEquals(js.getString("gravity") , "1 standard", "The gravity of the planet is 1 standard");

        Assert.assertEquals(js.getString("terrain") , "grasslands, mountains", "The terrain of the planet is grasslands, mountains");

        Assert.assertEquals(js.getString("surface_water") , "40", "The surface_water of the planet  is 40");

        Assert.assertEquals(js.getString("population") , "2000000000", "The population of the planet is 2000000000");

        Assert.assertEquals(js.getString("url") , "https://swapi.co/api/planets/2/", "The URL of the planet is https://swapi.co/api/planets/2/");

    }

}
