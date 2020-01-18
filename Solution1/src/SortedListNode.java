public class SortedListNode implements Comparable
{
    public int node = -1;
    public int reach = 0;

    public int compareTo(Object obj)
    {
        SortedListNode n = (SortedListNode)obj;
        if(n.reach > this.reach)
        {
            return 1;
        }
        else if(n.reach < this.reach)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}