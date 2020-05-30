package com.google.sps.classes;

import java.util.Comparator;

public class ChatComparator implements Comparator<Chatroom>
{
    @Override
    public int compare(Chatroom a, Chatroom b)
    {
        if (a.getTime().toDate().before(b.getTime().toDate()))
        {
            return 1;
        }
        else if(a.getTime().toDate().after(b.getTime().toDate()))
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}