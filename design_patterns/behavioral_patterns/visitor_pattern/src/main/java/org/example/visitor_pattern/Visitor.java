package org.example.visitor_pattern;

public interface Visitor {
    void visit(PopularFruit fruit);
    void visit(Apple fruit);
    void visit(Citrus fruit);
    void visit(Fuji fruit);
    void visit(Gala fruit);
    void visit(Orange fruit);
    void visit(Lemon fruit);
    void visit(GrapeFruit fruit);
}
