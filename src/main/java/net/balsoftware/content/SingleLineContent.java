package net.balsoftware.content;

import java.util.stream.Collectors;

import net.balsoftware.utilities.Callback;

public class SingleLineContent extends ContentLineBase
{
    final private int builderSize;
    final private Callback<Void,String> nameCallback;
    
    public SingleLineContent(
            Orderer orderer,
            Callback<Void,String> nameCallback,
            int builderSize)
    {
        super(orderer);
        this.nameCallback = nameCallback;
        this.builderSize = builderSize;
    }
    
    @Override
    public String execute()
    {
        StringBuilder builder = new StringBuilder(builderSize);
        builder.append(nameCallback.call(null));
        String elements = orderer.childrenUnmodifiable().stream()
//        		.peek(c -> System.out.println("child:" + c))
                .map(c -> c.toString())
                .collect(Collectors.joining(";"));
        if (! elements.isEmpty())
        {
            builder.append(";" + elements);
        }
        return builder.toString();
    }
}
