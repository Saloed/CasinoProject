package gameManager.gameMessageSystem;

import base.Abonent;
import base.Address;
import gameManager.GameManager;
import gameManager.GameRoulette;
import gameManager.GameSlotMachine;

/**
 * Created by user on 04.11.15.
 */
public class GameAddressService {
    private Address slotMachineAddress;
    private Address rouletteAddress;
    private Address gameManagerAddress;

    public void register(Abonent object) {

        if (object instanceof GameManager)
            gameManagerAddress = ((GameManager) object).getAddress();
        else if (object instanceof GameRoulette)
            rouletteAddress = ((GameRoulette) object).getAddress();
        else if (object instanceof GameSlotMachine)
            slotMachineAddress = ((GameSlotMachine) object).getAddress();

    }

    public Address getGameManagerAddress() {
        return gameManagerAddress;
    }

    public Address getSlotMachineAddress() {
        return slotMachineAddress;
    }

    public Address getRouletteAddress() {
        return rouletteAddress;
    }
}
