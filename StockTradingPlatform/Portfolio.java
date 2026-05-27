import java.util.HashMap;
public class Portfolio{
HashMap<String,Integer> holdings=new HashMap<>();
public void buyStock(String stock,int qty){
holdings.put(stock,holdings.getOrDefault(stock,0)+qty);
}
public void sellStock(String stock,int qty){
if(holdings.containsKey(stock)){
int current=holdings.get(stock);
if(current>=qty){
holdings.put(stock,current-qty);
}
}
}
public HashMap<String,Integer> getHoldings(){
return holdings;
}
}