package net.balsoftware.content;

import java.util.stream.Collectors;

public class SingleLineContent extends ContentLineBase
{
    final private int builderSize;
    final private String name;
    
    public SingleLineContent(
            Orderer orderer,
            String name,
            int builderSize)
    {
        super(orderer);
        this.name = name;
        this.builderSize = builderSize;
    }
    
    @Override
    public String execute()
    {
        StringBuilder builder = new StringBuilder(builderSize);
        builder.append(name);
        String elements = orderer.childrenUnmodifiable().stream()
                .map(c -> c.toString())
                .collect(Collectors.joining(";"));
        if (! elements.isEmpty())
        {
            builder.append(";" + elements);
        }
        return builder.toString();
    }
}
