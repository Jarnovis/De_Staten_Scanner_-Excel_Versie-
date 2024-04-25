import org.example.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class MatchesTest extends Driver{
    protected final RightData rightData = new RightData();
    protected final GUI_Upload upload = new GUI_Upload(rightData);
    protected final GUI_Commit commit = new GUI_Commit();
    protected final GUI_Search search = new GUI_Search(upload, commit, connector, rightData, true);
    @Test
    public void matchGood(){
        Driver website = new Driver(search, "https://hdr.undp.org/data-center/country-insights#/ranks", true);
        String expected = "Afghanistan | 182\n" +
                "Albania | 74\n" +
                "Algeria | 93\n" +
                "Andorra | 35\n" +
                "Angola | 150\n" +
                "Argentina | 48\n" +
                "Armenia | 76\n" +
                "Australia | 10\n" +
                "Austria | 22\n" +
                "Azerbaijan | 89\n" +
                "Bahrain | 34\n" +
                "Bangladesh | 129\n" +
                "Belarus | 69\n" +
                "Belgium | 12\n" +
                "Benin | 173\n" +
                "Bhutan | 125\n" +
                "Bosnia and Herzegovina | 80\n" +
                "Botswana | 114\n" +
                "Brazil | 89\n" +
                "Bulgaria | 70\n" +
                "Burkina Faso | 185\n" +
                "Burundi | 187\n" +
                "Cambodia | 148\n" +
                "Cameroon | 151\n" +
                "Canada | 18\n" +
                "Chad | 189\n" +
                "China | 75\n" +
                "Colombia | 91\n" +
                "Costa Rica | 64\n" +
                "Croatia | 39\n" +
                "Cuba | 85\n" +
                "Cyprus | 29\n" +
                "Denmark | 5\n" +
                "Djibouti | 171\n" +
                "Dominican Republic | 82\n" +
                "Ecuador | 83\n" +
                "Egypt | 105\n" +
                "El salvador | 127\n" +
                "Equatorial Guinea | 133\n" +
                "Eritrea | 175\n" +
                "Estonia | 31\n" +
                "Ethiopia | 176\n" +
                "Finland | 12\n" +
                "France | 28\n" +
                "Gabon | 123\n" +
                "Gambia | 174\n" +
                "Georgia | 60\n" +
                "Germany | 7\n" +
                "Ghana | 145\n" +
                "Greece | 33\n" +
                "Guatemala | 136\n" +
                "Guinea | 181\n" +
                "Guinea-Bissau | 179\n" +
                "Guyana | 95\n" +
                "Honduras | 138\n" +
                "Hungary | 47\n" +
                "Iceland | 3\n" +
                "India | 134\n" +
                "Indonesia | 112\n" +
                "Iraq | 128\n" +
                "Ireland | 7\n" +
                "Israel | 25\n" +
                "Italy | 30\n" +
                "Jamaica | 115\n" +
                "Japan | 24\n" +
                "Jordan | 99\n" +
                "Kazakhstan | 67\n" +
                "Kenya | 146\n" +
                "Kuwait | 49\n" +
                "Kyrgyzstan | 117\n" +
                "Latvia | 37\n" +
                "Lebanon | 109\n" +
                "Lesotho | 168\n" +
                "Liberia | 177\n" +
                "Libya | 92\n" +
                "Lithuania | 37\n" +
                "Luxembourg | 20\n" +
                "Madagascar | 177\n" +
                "Malawi | 172\n" +
                "Malaysia | 63\n" +
                "Maldives | 87\n" +
                "Mali | 188\n" +
                "Malta | 25\n" +
                "Mauritania | 164\n" +
                "Mexico | 77\n" +
                "Mongolia | 96\n" +
                "Montenegro | 50\n" +
                "Mozambique | 183\n" +
                "Myanmar | 144\n" +
                "Namibia | 142\n" +
                "Nepal | 146\n" +
                "Netherlands | 10\n" +
                "New Zealand | 16\n" +
                "Nicaragua | 130\n" +
                "Niger | 189\n" +
                "Nigeria | 161\n" +
                "Norway | 2\n" +
                "Oman | 59\n" +
                "Pakistan | 164\n" +
                "Panama | 57\n" +
                "Papua New Guinea | 154\n" +
                "Paraguay | 102\n" +
                "Peru | 87\n" +
                "Philippines | 113\n" +
                "Poland | 36\n" +
                "Portugal | 42\n" +
                "Qatar | 40\n" +
                "Romania | 53\n" +
                "Rwanda | 161\n" +
                "Saudi Arabia | 40\n" +
                "Senegal | 169\n" +
                "Serbia | 65\n" +
                "Sierra Leone | 184\n" +
                "Singapore | 9\n" +
                "Slovakia | 45\n" +
                "Slovenia | 22\n" +
                "Spain | 27\n" +
                "Sri Lanka | 78\n" +
                "Sudan | 170\n" +
                "Suriname | 124\n" +
                "Sweden | 5\n" +
                "Switzerland | 1\n" +
                "Tajikistan | 126\n" +
                "Thailand | 66\n" +
                "Togo | 163\n" +
                "Tunisia | 101\n" +
                "Turkmenistan | 94\n" +
                "Uganda | 159\n" +
                "Ukraine | 100\n" +
                "United Arab Emirates | 17\n" +
                "United Kingdom | 15\n" +
                "United States | 20\n" +
                "Uruguay | 52\n" +
                "Uzbekistan | 106\n" +
                "Yemen | 186\n" +
                "Zambia | 153\n" +
                "Zimbabwe | 159\n" +
                "137 matches out of Country";

        Assertions.assertEquals(expected, commit.getMatches().getInfo());
    }

    @Test
    public void notMatching(){
        Driver website = new Driver(search, "https://hdr.undp.org/data-center/country-insights#/ranks", true);

        String expected = "South Korea\n" +
                "Czech Republic\n" +
                "Chili\n" +
                "Macedonia\n" +
                "Moldavia\n" +
                "South Afrika\n" +
                "Bolivia\n" +
                "Kosovo\n" +
                "Ivory Coast\n" +
                "Tanzania\n" +
                "Vietnam\n" +
                "Morocco \n" +
                "Russia\n" +
                "Haïti\n" +
                "Turkey\n" +
                "Democratic Republic of Congo\n" +
                "Eswatini\n" +
                "Laos\n" +
                "Central-African Republic\n" +
                "Iran\n" +
                "Venezuela\n" +
                "Southern Sudan\n" +
                "missing matches: 22";

        Assertions.assertEquals(expected, commit.getNoMatches().getInfo());
        connector.close();

    }

    @AfterAll
    public static void close(){
        (new Driver()).stopDriver();
    }

}

