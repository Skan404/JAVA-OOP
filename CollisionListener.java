public interface CollisionListener {
    void onCollision(Organism attacker, Organism defender, CollisionResult result);
}