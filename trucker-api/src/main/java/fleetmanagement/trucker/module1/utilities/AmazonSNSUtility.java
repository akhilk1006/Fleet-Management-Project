package fleetmanagement.trucker.module1.utilities;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AmazonSNSUtility {

    @Autowired
    AmazonSNS snsClient;

    private Map<String, MessageAttributeValue> smsAttributes;

    public AmazonSNSUtility(){
        this.smsAttributes = new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Promotional") //Sets the type to promotional.
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("truckerapi") //The sender ID shown on the device.
                .withDataType("String"));
    }

    @Async
    public void sendSMS(String phoneNumber, String message){
        snsClient.publish(new PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber(phoneNumber)
                    .withMessageAttributes(this.smsAttributes));
    }

}
