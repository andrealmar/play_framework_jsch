package controllers;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Control {

    public enum EnvironmentOptions { Production, NonProduction };
    private String user = "admin";
    private String sshKeyPRD = "/home/workspace/testeJava/admin_open.ppk";
    private String sshPassPRD = "#########"; //password hidden for security reasons
    private String sshKeyHML = "/home/workspace/testeJava/admin_tst_priv_openssh.ppk";


    //constructor
    public Control() { }

    private com.jcraft.jsch.JSch createJSchObjetc(EnvironmentOptions env) throws Exception {
        try {
            com.jcraft.jsch.JSch jsch = new JSch();
            if (env == EnvironmentOptions.Production) {
                jsch.addIdentity(sshKeyPRD,sshPassPRD);
            }
            else {
                jsch.addIdentity(sshKeyHML);
            }
            return jsch;
        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    //=========================================================================================
    // Metodo...: listOHSParam
    // Descricao: List all parameters of the HTTPD.CONF file from OHS - Oracle HTTP Server
    //=========================================================================================
    public String listOHSParam(EnvironmentOptions env, String server, String ohsInstanceHome) throws Exception {

        ChannelExec channel=null;
        Session session=null;
        String result="";

        try{
            // Create object to send the SSH command
            JSch jsch = createJSchObjetc(env);

            // Create object Session and do the SSH conection
            session=jsch.getSession(user, server, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // Defines the command
            String command = "cat " + ohsInstanceHome + "/config/OHS/ohs1/httpd.conf";

            // Create object Channel
            channel=(ChannelExec)session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream error=new ByteArrayOutputStream();
            channel.setErrStream(error);
            channel.connect();

            DataInputStream dataIn = new DataInputStream(channel.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(channel.getOutputStream());

            // Get the response and add to the HashMap
            String line;
            while ((line = dataIn.readLine()) != null) {
                result += line + "\n";
            }

            dataIn.close();
            dataOut.close();
        }
        catch (Exception e) {
            throw new Exception(e);
        }
        finally {
            if (channel != null){
                channel.disconnect();
                session.disconnect();
            }
        }

        return result;
    }

}
