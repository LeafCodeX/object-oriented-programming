package Project2.Extensions;

public class SpecialAbility {
    private final int MAGIC_ELIXIR_DURATION = 5, COOLDOWN = 10;
    private boolean canMagicElixirBeActivated, isMagicElixirActive;
    private int magicElixirDuration, cooldown;

    public int getMagicElixirDuration() {
        return magicElixirDuration;
    }

    public void setMagicElixirDuration(int magicElixirDuration) {
        this.magicElixirDuration = magicElixirDuration;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean getCanMagicElixirBeActivated() {
        return canMagicElixirBeActivated;
    }

    public void setCanMagicElixirBeActivated(boolean canMagicElixirBeActivated) {
        this.canMagicElixirBeActivated = canMagicElixirBeActivated;
    }

    public boolean getIsMagicElixirActive() {
        return isMagicElixirActive;
    }

    public void setMagicElixirActive(boolean isMagicElixirActive) {
        this.isMagicElixirActive = isMagicElixirActive;
    }

    public void DeactivateMagicElixir() {
        isMagicElixirActive = false;
    }
    
    public SpecialAbility() {
        canMagicElixirBeActivated = true;
        isMagicElixirActive = false;
        magicElixirDuration = 0;
        cooldown = 0;
    }

    public void check() {
        if (cooldown > 0) cooldown--;
        if (magicElixirDuration > 0) magicElixirDuration--;
        if (magicElixirDuration == 0) DeactivateMagicElixir();
        if (cooldown == 0) canMagicElixirBeActivated = true;
    }

    public void ActiveMagicElixir() {
        if (cooldown == 0) {
            magicElixirDuration = MAGIC_ELIXIR_DURATION;
            cooldown = COOLDOWN;
            isMagicElixirActive = true;
            canMagicElixirBeActivated = false;
        }
    }
}

