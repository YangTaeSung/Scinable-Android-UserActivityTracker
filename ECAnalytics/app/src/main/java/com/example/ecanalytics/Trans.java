package com.example.ecanalytics;

import java.util.ArrayList;

/*
   req: null,  얘는 뭐지...?
   type: 'C',  트랜잭션의 타입을 구분 C: create, U: update, W: withdrow
   order: [],
   items: [],
   member: [],
   claim: []

   var _addMember = function() {
                Scinable.Trans.type = 'C';

                for(i=0; i<9; i++){
                    Scinable.Trans.member.push(arguments[i]); //arguments를 이용해 동적으로 변수를 받는거같다.
                }

                Scinable.Trans.member.push('1'); //valid_yn ......이건 뭐지

                var jo;
                if(arguments.length = 10) {
                    jo = arguments[9];  //마지막은 한개의 밸류가 아닌 정보를 담고있는 배열인가
                }
                for(i=1; i<6; i++) {
                    if(jo && jo[i]) {
                        Scinable.Trans.member.push(jo[i]);
                    } else {
                        Scinable.Trans.member.push('');
                    }
                }
            }
 */
public class Trans {
    protected char type;
    private ArrayList<String> order;
    private ArrayList<String[]> items;
    private ArrayList<String> member;
    private ArrayList<String[]> claim;

    public Trans(){
        order = new ArrayList<>();
        items = new ArrayList<>();
        member = new ArrayList<>();
        claim = new ArrayList<>();
    }

    public void addItem(String[] items){
        this.items.add(items);
    }

    public void setMember(String[] member){
        int i;
        for(i = 0 ; i < 9; i++){
            this.member.add(member[i]);
        }
        this.member.add("1"); //valid_yn

        if(member.length > 9){
            while(i < member.length){
                if(member[i].isEmpty()){
                    this.member.add("");
                }
                else{
                    this.member.add(member[i]);
                }
                i++;
            }
        }
    }

    public void addOrder(String[] order){
        //this.order.add(order);
    }

    public void addClaim(String[] claim){
        this.claim.add(claim);
    }

}
