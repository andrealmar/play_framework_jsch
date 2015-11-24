package controllers;
 
import oi.siebel.Control; //that's the Class I created to send the SSH commands to an UNIX shell
import play.mvc.Controller;
import play.mvc.Result;
import java.util.*;
import java.lang.*;
 
public class Application extends Controller {
 
        public static Result index() {
 
            //variable that will receive the result of the method listOHSParam
            String result = null;
 
            try {
                oi.siebel.Control a = new Control();
                HashMap<String, String[]> result = null;
                HashMap<String, Integer> result2 = null;
                String[] result3 = null;
                HashMap<String, String> result4 = null;
                Iterator it = null;
 
                resultado = a.listOHSParam(Control.EnvironmentOptions.NonProduction, "server01", "/siebel/MiddlewareHome/Oracle_WT1/instances/instance1");
 
            }
            catch (Exception ex){
 
            }
            finally {
 
            }
 
            return ok(result);
        }
 
}
