package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food
{
    public String name;
    public String exotic;
    public Type type;
}
