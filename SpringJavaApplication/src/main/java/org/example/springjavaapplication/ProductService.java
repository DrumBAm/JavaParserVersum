package org.example.springjavaapplication;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public List<Product> searchProducts(String query) throws IOException {
        String url = "https://versum.ua/search/?search=" + query;
        List<Product> products = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        Elements items = doc.select(".catalog-item");

        for (Element item : items) {
            Element titleElement = item.selectFirst(".catalog-title a");
            String title = titleElement.text();
            String link = titleElement.attr("href");

            Element priceElement = item.selectFirst(".catalog-price .price-value");
            String price = priceElement.text().replaceAll("[^\\d]", "");

            products.add(new Product(title, link, price));
        }

        return products;
    }

    public byte[] generateExcelBytes(List<Product> products) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        int rowNum = 0;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getTitle());
            row.createCell(1).setCellValue(product.getLink());
            row.createCell(2).setCellValue(Double.parseDouble(product.getPrice()));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
