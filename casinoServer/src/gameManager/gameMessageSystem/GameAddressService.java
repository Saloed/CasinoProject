package gameManager.gameMessageSystem;

import base.Abonent;
import base.Address;
import gameManager.GameManager;
import gameManager.GameRoulette;
import gameManager.GameSlotMachine;

public class GameAddressService {
    private Address slotMachineAddress;
    private Address rouletteAddress;
    private Address gameManagerAddress;

    public void register(Abonent object) {

        if (object instanceof GameManager)
            gameManagerAddress = object.getAddress();
        else if (object instanceof GameRoulette)
            rouletteAddress = object.getAddress();
        else if (object instanceof GameSlotMachine)
            slotMachineAddress = object.getAddress();

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
