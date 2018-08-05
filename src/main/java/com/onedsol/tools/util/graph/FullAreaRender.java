package com.onedsol.tools.util.graph;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AreaRendererEndType;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleEdge;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

public class FullAreaRender extends AreaRenderer {

    /**
     * A flag that controls how the ends of the areas are drawn.
     */
    private AreaRendererEndType endType;

    /**
     * Draw a single data item.
     *
     * @param g2         the graphics device.
     * @param state      the renderer state.
     * @param dataArea   the data plot area.
     * @param plot       the plot.
     * @param domainAxis the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset    the dataset.
     * @param row        the row index (zero-based).
     * @param column     the column index (zero-based).
     * @param pass       the pass index.
     */
    @Override
    public void drawItem(Graphics2D g2, CategoryItemRendererState state,
                         Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis,
                         ValueAxis rangeAxis, CategoryDataset dataset, int row, int column,
                         int pass) {

        // do nothing if item is not visible or null
        if (!getItemVisible(row, column)) {
            return;
        }
        Number value = dataset.getValue(row, column);
        if (value == null) {
            return;
        }
        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge axisEdge = plot.getDomainAxisEdge();
        int count = dataset.getColumnCount();
        float x0 = (float) getCategoryStart(column, count, dataArea,
                axisEdge, domainAxis);
        float x1 = (float) domainAxis.getCategoryMiddle(column, count,
                dataArea, axisEdge);
        float x2 = (float) getCategoryEnd(column, count, dataArea,
                axisEdge, domainAxis);

        x0 = Math.round(x0);
        x1 = Math.round(x1);
        x2 = Math.round(x2);

        if (this.endType == AreaRendererEndType.TRUNCATE) {
            if (column == 0) {
                x0 = x1;
            } else if (column == getColumnCount() - 1) {
                x2 = x1;
            }
        }

        double yy1 = value.doubleValue();

        double yy0 = 0.0;
        if (this.endType == AreaRendererEndType.LEVEL) {
            yy0 = yy1;
        }
        if (column > 0) {
            Number n0 = dataset.getValue(row, column - 1);
            if (n0 != null) {
                yy0 = (n0.doubleValue() + yy1) / 2.0;
            }
        }

        double yy2 = 0.0;
        if (column < dataset.getColumnCount() - 1) {
            Number n2 = dataset.getValue(row, column + 1);
            if (n2 != null) {
                yy2 = (n2.doubleValue() + yy1) / 2.0;
            }
        } else if (this.endType == AreaRendererEndType.LEVEL) {
            yy2 = yy1;
        }

        RectangleEdge edge = plot.getRangeAxisEdge();
        float y0 = (float) rangeAxis.valueToJava2D(yy0, dataArea, edge);
        float y1 = (float) rangeAxis.valueToJava2D(yy1, dataArea, edge);
        float y2 = (float) rangeAxis.valueToJava2D(yy2, dataArea, edge);
        float yz = (float) rangeAxis.valueToJava2D(0.0, dataArea, edge);
        double labelXX = x1;
        double labelYY = y1;
        g2.setPaint(getItemPaint(row, column));
        g2.setStroke(getItemStroke(row, column));

        GeneralPath area = new GeneralPath();

        if (orientation == PlotOrientation.VERTICAL) {
            area.moveTo(x0, yz);
            area.lineTo(x0, y0);
            area.lineTo(x1, y1);
            area.lineTo(x2, y2);
            area.lineTo(x2, yz);
        } else if (orientation == PlotOrientation.HORIZONTAL) {
            area.moveTo(yz, x0);
            area.lineTo(y0, x0);
            area.lineTo(y1, x1);
            area.lineTo(y2, x2);
            area.lineTo(yz, x2);
            double temp = labelXX;
            labelXX = labelYY;
            labelYY = temp;
        }
        area.closePath();

        g2.setPaint(getItemPaint(row, column));
        g2.fill(area);

        // draw the item labels if there are any...
        if (isItemLabelVisible(row, column)) {
            drawItemLabel(g2, orientation, dataset, row, column, labelXX,
                    labelYY, (value.doubleValue() < 0.0));
        }

        // submit the current data point as a crosshair candidate
        int datasetIndex = plot.indexOf(dataset);
        updateCrosshairValues(state.getCrosshairState(),
                dataset.getRowKey(row), dataset.getColumnKey(column), yy1,
                datasetIndex, x1, y1, orientation);

        // add an item entity, if this information is being collected
        EntityCollection entities = state.getEntityCollection();
        if (entities != null) {
            addItemEntity(entities, dataset, row, column, area);
        }

    }

    /**
     * Returns the starting coordinate for the specified category.
     *
     * @param category      the category.
     * @param categoryCount the number of categories.
     * @param area          the data area.
     * @param edge          the axis location.
     * @param domainAxis    domain axis category
     * @return The coordinate.
     * @see #getCategoryMiddle(int, int, Rectangle2D, RectangleEdge, CategoryAxis)
     * @see #getCategoryEnd(int, int, Rectangle2D, RectangleEdge, CategoryAxis)
     */
    public double getCategoryStart(int category, int categoryCount,
                                   Rectangle2D area, RectangleEdge edge, CategoryAxis domainAxis) {

        double result = 0.0;
        if ((edge == RectangleEdge.TOP) || (edge == RectangleEdge.BOTTOM)) {
            result = area.getX() + area.getWidth() * domainAxis.getLowerMargin();
        } else if ((edge == RectangleEdge.LEFT)
                || (edge == RectangleEdge.RIGHT)) {
            result = area.getMinY() + area.getHeight() * domainAxis.getLowerMargin();
        }

        double categorySize = calculateCategorySize(categoryCount, area, edge, domainAxis);
        double categoryGapWidth = calculateCategoryGapSize(categoryCount, area, edge);

        result = result + category * (categorySize + categoryGapWidth);
        return result;
    }

    /**
     * Calculates the size (width or height, depending on the location of the
     * axis) of a category without category margin.
     *
     * @param categoryCount the number of categories.
     * @param area          the area within which the categories will be drawn.
     * @param edge          the axis location.
     * @param domainAxis    domain axis category
     * @return The category size.
     */
    protected double calculateCategorySize(int categoryCount, Rectangle2D area,
                                           RectangleEdge edge, CategoryAxis domainAxis) {
        double result;
        double available = 0.0;

        if ((edge == RectangleEdge.TOP) || (edge == RectangleEdge.BOTTOM)) {
            available = area.getWidth();
        } else if ((edge == RectangleEdge.LEFT)
                || (edge == RectangleEdge.RIGHT)) {
            available = area.getHeight();
        }
        if (categoryCount > 1) {
            result = available * (1 - domainAxis.getLowerMargin() - domainAxis.getUpperMargin()
                    - .0);
            result = result / categoryCount;
        } else {
            result = available * (1 - domainAxis.getLowerMargin() - domainAxis.getUpperMargin());
        }
        return result;
    }

    /**
     * Calculates the size (width or height, depending on the location of the
     * axis) of a category gap, without having category margin.
     *
     * @param categoryCount the number of categories.
     * @param area          the area within which the categories will be drawn.
     * @param edge          the axis location.
     * @return The category gap width.
     */
    protected double calculateCategoryGapSize(int categoryCount,
                                              Rectangle2D area, RectangleEdge edge) {

        double result = 0.0;
        double available = 0.0;

        if ((edge == RectangleEdge.TOP) || (edge == RectangleEdge.BOTTOM)) {
            available = area.getWidth();
        } else if ((edge == RectangleEdge.LEFT)
                || (edge == RectangleEdge.RIGHT)) {
            available = area.getHeight();
        }

        if (categoryCount > 1) {
            result = available * .0 / (categoryCount - 1);
        }
        return result;
    }

    /**
     * Returns the end coordinate for the specified category.
     *
     * @param category      the category.
     * @param categoryCount the number of categories.
     * @param area          the data area.
     * @param edge          the axis location.
     * @param domainAxis    domain axis category
     * @return The coordinate.
     * @see #getCategoryStart(int, int, Rectangle2D, RectangleEdge, CategoryAxis)
     * @see #getCategoryMiddle(int, int, Rectangle2D, RectangleEdge, CategoryAxis)
     */
    public double getCategoryEnd(int category, int categoryCount,
                                 Rectangle2D area, RectangleEdge edge, CategoryAxis domainAxis) {
        return getCategoryStart(category, categoryCount, area, edge, domainAxis)
                + calculateCategorySize(categoryCount, area, edge, domainAxis);
    }

    /**
     * Returns the middle coordinate for the specified category.
     *
     * @param category      the category.
     * @param categoryCount the number of categories.
     * @param area          the data area.
     * @param edge          the axis location.
     * @param domainAxis    domain axis category
     * @return The coordinate.
     * @see #getCategoryStart(int, int, Rectangle2D, RectangleEdge, CategoryAxis)
     * @see #getCategoryEnd(int, int, Rectangle2D, RectangleEdge, CategoryAxis)
     */
    public double getCategoryMiddle(int category, int categoryCount,
                                    Rectangle2D area, RectangleEdge edge, CategoryAxis domainAxis) {

        if (category < 0 || category >= categoryCount) {
            throw new IllegalArgumentException("Invalid category index: "
                    + category);
        }
        return getCategoryStart(category, categoryCount, area, edge, domainAxis)
                + calculateCategorySize(categoryCount, area, edge, domainAxis) / 2;

    }


}
