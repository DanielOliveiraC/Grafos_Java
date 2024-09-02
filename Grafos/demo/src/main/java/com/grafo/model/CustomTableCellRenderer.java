package com.grafo.model;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value instanceof Number) {
            double weight = ((Number) value).doubleValue();

            // Ajusta a cor de fundo baseado no peso
            int intensity = (int) Math.min(255, Math.max(0, weight * 20));
            Color backgroundColor = new Color(255 - intensity, 255 - intensity, 255); // Cor que varia com o peso
            cell.setBackground(backgroundColor);

            // Ajusta a cor da fonte baseado no peso
            Color textColor = (weight > 5) ? Color.WHITE : Color.BLACK; // Exemplo de lógica
            cell.setForeground(textColor);
        } else {
            cell.setBackground(Color.WHITE);
            cell.setForeground(Color.BLACK); // Cor padrão para texto
        }

        return cell;
    }
}
