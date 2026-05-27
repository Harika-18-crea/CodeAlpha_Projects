public class Transaction{
public static boolean buy(User user,Stock stock,int qty,Portfolio portfolio){
double total=stock.getPrice()*qty;
if(user.getBalance()>=total){
user.deductBalance(total);
portfolio.buyStock(stock.getName(),qty);
return true;
}
return false;
}
public static boolean sell(User user,Stock stock,int qty,Portfolio portfolio){
portfolio.sellStock(stock.getName(),qty);
user.addBalance(stock.getPrice()*qty);
return true;
}
}