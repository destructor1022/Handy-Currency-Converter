package com.toughproject.handycurrencyconverter;


import java.util.ArrayList;


public class Currency {
    public String gamma;

    public ArrayList<String> currNamesList;
    public ArrayList<Integer> currFlagsList;

    public ArrayList<Integer> current;

    public ArrayList<Boolean> loadVisible;

    public ArrayList<String> names;
    public ArrayList<Integer> flags;
    public ArrayList<Integer> visible;

    public Integer getFlag(int i) {
        return flags.get(current.get(i));
    }

    public String getName(int i) {
        return names.get(current.get(i));
    }

    public void addCurrency(int i) {
        int a = this.current.get(i);
        a += 1;
        if(a >= this.names.size()) {
            a = 0;
        }
        this.current.set(i, a);
    }

    public void subCurrency(int i) {
        int a = this.current.get(i);
        a -= 1;
        if(a < 0) {
            a = this.names.size() - 1;
        }
        this.current.set(i, a);
    }

    public ArrayList<Integer> convertGamma(String gamma) {
        ArrayList<Integer> hi = new ArrayList<>();
        for(int i = 0; i < gamma.length(); i++) {
            hi.add(Character.getNumericValue(gamma.charAt(i)));
        }
        return hi;
    }

    public ArrayList<Integer> convertFlags(ArrayList<Integer> q) {
        ArrayList<Integer> t = new ArrayList<>();
        for(int i = 0; i < q.size(); i++) {
            if(q.get(i) == 1) {
                t.add(currFlagsList.get(i));
            }
        }
        return t;
    }

    public ArrayList<String> convertNames(ArrayList<Integer> q) {
        ArrayList<String> t = new ArrayList<>();
        for(int i = 0; i < q.size(); i++) {
            if(q.get(i) == 1) {
                t.add(currNamesList.get(i));
            }
        }
        return t;
    }

    public Currency(String gamma) {
        currNamesList = new ArrayList<>();
        currFlagsList = new ArrayList<>();

        addAllCurrencies();

        current = new ArrayList<>();
            current.add(0);
            current.add(1);

        loadVisible = new ArrayList<>();
            loadVisible.add(false);
            loadVisible.add(false);

        this.gamma = gamma;

        if(this.gamma.length() != currNamesList.size()) {
            this.gamma = "default";
        }

        if(this.gamma.equals("default")) {
            StringBuilder g = new StringBuilder();
            for(int i = 0; i < currNamesList.size(); i++) {
                if(i <= 8) {
                    g.append("1");
                } else {
                    g.append("0");
                }
            }

            this.gamma = g.toString();
        }

        this.visible = convertGamma(this.gamma);
        this.flags = convertFlags(this.visible);
        this.names = convertNames(this.visible);
    }

    public void addAllCurrencies() {
        //common world currencies (0-8)
        currNamesList.add("USD");
        currFlagsList.add(R.drawable.usd);
        currNamesList.add("EUR");
        currFlagsList.add(R.drawable.eur);
        currNamesList.add("GBP");
        currFlagsList.add(R.drawable.gbp);
        currNamesList.add("CNY");
        currFlagsList.add(R.drawable.cny);
        currNamesList.add("JPY");
        currFlagsList.add(R.drawable.jpy);
        currNamesList.add("CHF");
        currFlagsList.add(R.drawable.chf);
        currNamesList.add("CAD");
        currFlagsList.add(R.drawable.cad);
        currNamesList.add("INR");
        currFlagsList.add(R.drawable.inr);
        currNamesList.add("AUD");
        currFlagsList.add(R.drawable.aud);

        //Middle East (9-17)
        currNamesList.add("AED");
        currFlagsList.add(R.drawable.aed);
        currNamesList.add("BHD");
        currFlagsList.add(R.drawable.bhd);
        currNamesList.add("IRR");
        currFlagsList.add(R.drawable.irr);
        currNamesList.add("ILS");
        currFlagsList.add(R.drawable.ils);
        currNamesList.add("KWD");
        currFlagsList.add(R.drawable.kwd);
        currNamesList.add("OMR");
        currFlagsList.add(R.drawable.omr);
        currNamesList.add("QAR");
        currFlagsList.add(R.drawable.qar);
        currNamesList.add("SAR");
        currFlagsList.add(R.drawable.sar);
        currNamesList.add("TRY");
        currFlagsList.add(R.drawable.tryy);

        //Americas (18-24)
        currNamesList.add("ARS");
        currFlagsList.add(R.drawable.ars);
        currNamesList.add("BRL");
        currFlagsList.add(R.drawable.brl);
        currNamesList.add("CLP");
        currFlagsList.add(R.drawable.clp);
        currNamesList.add("COP");
        currFlagsList.add(R.drawable.cop);
        currNamesList.add("MXN");
        currFlagsList.add(R.drawable.mxn);
        currNamesList.add("TTD");
        currFlagsList.add(R.drawable.ttd);
        currNamesList.add("VEF");
        currFlagsList.add(R.drawable.vef);

        //Europe (25-34)
        currNamesList.add("BGN");
        currFlagsList.add(R.drawable.bgn);
        currNamesList.add("CZK");
        currFlagsList.add(R.drawable.czk);
        currNamesList.add("DKK");
        currFlagsList.add(R.drawable.dkk);
        currNamesList.add("HRK");
        currFlagsList.add(R.drawable.hrk);
        currNamesList.add("ISK");
        currFlagsList.add(R.drawable.isk);
        currNamesList.add("NOK");
        currFlagsList.add(R.drawable.nok);
        currNamesList.add("PLN");
        currFlagsList.add(R.drawable.pln);
        currNamesList.add("RON");
        currFlagsList.add(R.drawable.ron);
        currNamesList.add("RUB");
        currFlagsList.add(R.drawable.rub);
        currNamesList.add("SEK");
        currFlagsList.add(R.drawable.sek);

        //Asia & Pacific (35-48)
        currNamesList.add("BND");
        currFlagsList.add(R.drawable.bnd);
        currNamesList.add("HKD");
        currFlagsList.add(R.drawable.hkd);
        currNamesList.add("IDR");
        currFlagsList.add(R.drawable.idr);
        currNamesList.add("KZT");
        currFlagsList.add(R.drawable.kzt);
        currNamesList.add("KRW");
        currFlagsList.add(R.drawable.krw);
        currNamesList.add("LKR");
        currFlagsList.add(R.drawable.lkr);
        currNamesList.add("MYR");
        currFlagsList.add(R.drawable.myr);
        currNamesList.add("NPR");
        currFlagsList.add(R.drawable.npr);
        currNamesList.add("NZD");
        currFlagsList.add(R.drawable.nzd);
        currNamesList.add("PHP");
        currFlagsList.add(R.drawable.php);
        currNamesList.add("PKR");
        currFlagsList.add(R.drawable.pkr);
        currNamesList.add("SGD");
        currFlagsList.add(R.drawable.sgd);
        currNamesList.add("THB");
        currFlagsList.add(R.drawable.thb);
        currNamesList.add("TWD");
        currFlagsList.add(R.drawable.twd);

        //Africa (49-52)
        currNamesList.add("BWP");
        currFlagsList.add(R.drawable.bwp);
        currNamesList.add("LYD");
        currFlagsList.add(R.drawable.lyd);
        currNamesList.add("MUR");
        currFlagsList.add(R.drawable.mur);
        currNamesList.add("ZAR");
        currFlagsList.add(R.drawable.zar);



    }

}
