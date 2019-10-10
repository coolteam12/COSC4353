package sample;

// $Id$
public class Foo
{
    static public void main( String args[] ) throws Exception {
        System.out.println( "foo! "+args[0]+" "+args[1] );
        new Bar( args[0], args[1] );
    }
}
