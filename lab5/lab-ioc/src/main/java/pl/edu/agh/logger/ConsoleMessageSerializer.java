package pl.edu.agh.logger;


import com.google.inject.Inject;

public class ConsoleMessageSerializer  implements IMessageSerializer
{
	@Inject
	public ConsoleMessageSerializer()
	{
	}

	@Override
	public void serializeMessage(String message) {
		System.out.println(message);
	}
	
	
	
}
