/**
 * Created by leishuzhou on 2016/11/29.
 */
public class Test2 {
    public static void main(String args[]) {
       //x块钱，y钱一瓶，m个空瓶换一瓶，我可以喝多少瓶酒？
       int drinkBottle=drinkWine(61,1,2);
        System.out.println(drinkBottle);
    }



    public static int drinkWine(double x,double y ,int m) {
        int drinkTotal = (int) (x / y);
        return  emptyTotal(drinkTotal,drinkTotal,m);
    }

    public static int emptyTotal(int drinkTotal,int emptyBottle,int m){
        if(emptyBottle>=m){
            drinkTotal+=emptyBottle/m;
            emptyBottle=emptyBottle/m + emptyBottle % m;
            System.out.println("emptyBottle---------"+emptyBottle);
            drinkTotal=emptyTotal(drinkTotal,emptyBottle,m);
        }
        return drinkTotal;

    }




}
