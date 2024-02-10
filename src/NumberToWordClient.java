import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class NumberToWordClient {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Number to Word Converter");
        frame.setSize(800, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel inputLabel = new JLabel("Enter Number:");
        inputLabel.setBounds(10, 20, 100, 25);
        panel.add(inputLabel);

        JTextField textField = new JTextField(20);
        textField.setBounds(120, 20, 165, 25);
        panel.add(textField);

        JButton button = new JButton("Convert");
        button.setBounds(120, 60, 100, 25);
        panel.add(button);

        JLabel outputLabelTitle = new JLabel("Converted Word:");
        outputLabelTitle.setBounds(10, 100, 100, 25);
        panel.add(outputLabelTitle);

        JLabel outputLabel = new JLabel("");
        outputLabel.setBounds(120, 100, 600, 25);
        panel.add(outputLabel);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numberStr = textField.getText();
                try {
                    URL url = new URL("https://www.dataaccess.com/webservicesserver/numberconversion.wso");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");

                    // Enable redirects
                    HttpURLConnection.setFollowRedirects(true);

                    String xmlInputString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                            + "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
                            + "<soap12:Body>"
                            + "<NumberToWords xmlns=\"http://www.dataaccess.com/webservicesserver/\">"
                            + "<ubiNum>" + numberStr + "</ubiNum>"
                            + "</NumberToWords>"
                            + "</soap12:Body>"
                            + "</soap12:Envelope>";

                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    byte[] input = xmlInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String output;
                    StringBuilder response = new StringBuilder();

                    while ((output = in.readLine()) != null) {
                        response.append(output);
                    }

                    in.close();

                    // Parse XML response
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    InputSource is = new InputSource(new StringReader(response.toString()));
                    Document doc = db.parse(is);
                    NodeList nodes = doc.getElementsByTagName("m:NumberToWordsResult");
                    String result = nodes.item(0).getTextContent();

                    // Print result on GUI
                    outputLabel.setText(result);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
