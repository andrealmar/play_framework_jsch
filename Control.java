

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
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79
80
81
82
83
84
85
86
87
88
89
90
91
92
93
94
95
96
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
