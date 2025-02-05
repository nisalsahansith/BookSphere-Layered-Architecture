package com.project.booksphere.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseTM {
    private String itemId;
    private String itemName;
    private double qty;
    private double qtyPrice;
    private double price;
    private Button button;
}
