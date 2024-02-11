This class is a simple GUI application that converts a number into words. It uses a SOAP-based web service provided by www.dataaccess.com to perform the conversion.

How to use :
1.Run the NumberToWordClient class. This will open a window titled “Number to Word Converter”.
2.In the text field labeled “Enter Number:”, input the number you want to convert to words.
3.Click the “Convert” button. The application will then send a request to the web service to convert the number to words.
4.The converted number will be displayed in the “Converted Word:” field.

Functionality :
The NumberToWordClient class creates a simple Swing GUI with a text field for input and a label for output. 
When the “Convert” button is clicked, it takes the number from the text field, sends a SOAP request to the web service, 
and parses the XML response to extract the converted number in words. 
The result is then displayed in the “Converted Word:” field.
