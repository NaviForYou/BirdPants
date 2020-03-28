/*
JSON 데이터를 파싱한다.
JSON 데이터는 assets 폴더에 넣는다.
url로 하는게 나으면 추후에 그걸로 바꾸겠음

1차 제작 : 이지우_0328 (참고자료: https://lktprogrammer.tistory.com/175  https://humble.tistory.com/20) + 코드 오류 있음(27)

 */

package com.example.naviforyou;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonDataParse<SeoulDisabledFacilities> {

    private String getJsonString() {
        String json = "";

        try {
            //여기 해결해야함
            InputStream is = getAssets().open("SeoulDisabledFacilities.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }

    public class SeoulDisabledFacilities {
        private String bldg_x;
        private String bldg_y;
        private String information;
        private String fax;
        private String tel;
        private String sitemap_alt;
        private String addr;
        private String purpose;
        private String found_date;
        private int persons;
        private String homepage;
        private String sitemap;
        private String area_name;
        private String name;
        private String foundation;
        private String sitemap_desc;
        private String div_name;


        public String getBldg_x() {
            return bldg_x;
        }

        public String getBldg_y() {
            return bldg_y;
        }

        public String getInformation() {
            return information;
        }

        public String getFax() {
            return fax;
        }

        public String getTel() {
            return tel;
        }

        public String getSitemap_alt() {
            return sitemap_alt;
        }

        public String getAddr() {
            return addr;
        }

        public String getPurpose() {
            return purpose;
        }

        public String getFound_date() {
            return found_date;
        }

        public int getpersons() {
            return persons;
        }

        public String getHomepage() {
            return homepage;
        }

        public String getSitemap() {
            return sitemap;
        }

        public String getArea_name() {
            return area_name;
        }

        public String getName() {
            return name;
        }

        public String getFoundation() {
            return foundation;
        }

        public String getSitemap_desc() {
            return sitemap_desc;
        }

        public String getDiv_name() {
            return div_name;
        }


        public void setBldg_x(String bldg_x) {
            this.bldg_x = bldg_x;

        }

        public void setBldg_y(String bldg_y) {
            this.bldg_y = bldg_y;

        }

        public void setInformation(String information) {
            this.information = information;

        }

        public void setFax(String fax) {
            this.fax = fax;

        }

        public void setTel(String tel) {
            this.tel = tel;

        }

        public void setSitemap_alt(String sitemap_alt) {
            this.sitemap_alt = sitemap_alt;

        }

        public void setAddr(String addr) {
            this.addr = addr;

        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;

        }

        public void setFound_date(String found_date) {
            this.found_date = found_date;

        }

        public void setPersons(int persons) {
            this.persons = persons;

        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;

        }

        public void setSitemap(String sitemap) {
            this.sitemap = sitemap;

        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;

        }

        public void setName(String name) {
            this.name = name;

        }

        public void setFoundation(String foundation) {
            this.foundation = foundation;

        }

        public void setSitemap_desc(String sitemap_desc) {
            this.sitemap_desc = sitemap_desc;

        }

        public void setDiv_name(String div_name) {
            this.div_name = div_name;

        }


        private void jsonParsing(String json) {
            try {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray seoulDisabledFacilitiesArray = jsonObject.getJSONArray("SeoulDisabledFacilities");

                ArrayList<SeoulDisabledFacilities> seoulDisabledFacilitiesList = new ArrayList<>();

                for (int i = 0; i < seoulDisabledFacilitiesArray.length(); i++) {
                    JSONObject seoulDisabledFacilitiesObject = seoulDisabledFacilitiesArray.getJSONObject(i);

                    SeoulDisabledFacilities seoulDisabledFacilities = new SeoulDisabledFacilities();

                    seoulDisabledFacilities.setBldg_x(seoulDisabledFacilitiesObject.getString("Bldg_x"));
                    seoulDisabledFacilities.setBldg_y(seoulDisabledFacilitiesObject.getString("Bldg_y"));
                    seoulDisabledFacilities.setInformation(seoulDisabledFacilitiesObject.getString("Information"));
                    seoulDisabledFacilities.setFax(seoulDisabledFacilitiesObject.getString("Fax"));
                    seoulDisabledFacilities.setSitemap_alt(seoulDisabledFacilitiesObject.getString("Sitemap_alt"));
                    seoulDisabledFacilities.setAddr(seoulDisabledFacilitiesObject.getString("Addr"));
                    seoulDisabledFacilities.setPurpose(seoulDisabledFacilitiesObject.getString("Purpose"));
                    seoulDisabledFacilities.setFound_date(seoulDisabledFacilitiesObject.getString("Found_date"));
                    seoulDisabledFacilities.setPersons(seoulDisabledFacilitiesObject.getInt("Persons"));
                    seoulDisabledFacilities.setHomepage(seoulDisabledFacilitiesObject.getString("Homepage"));
                    seoulDisabledFacilities.setSitemap(seoulDisabledFacilitiesObject.getString("Sitemap"));
                    seoulDisabledFacilities.setArea_name(seoulDisabledFacilitiesObject.getString("Area_name"));
                    seoulDisabledFacilities.setName(seoulDisabledFacilitiesObject.getString("Name"));
                    seoulDisabledFacilities.setFoundation(seoulDisabledFacilitiesObject.getString("Foundation"));
                    seoulDisabledFacilities.setSitemap_desc(seoulDisabledFacilitiesObject.getString("Sitemap_desc"));
                    seoulDisabledFacilities.setDiv_name(seoulDisabledFacilitiesObject.getString("Div_name"));


                    seoulDisabledFacilitiesList.add(seoulDisabledFacilities);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}