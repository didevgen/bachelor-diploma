import { NamedCardHolder } from '../cardh-holders/holders.model';
import { Room } from '../rooms/room.models';

export class HistoryEvent {
  public code: number;
  public eventName: string;
  public uuid: string;
}

export class AccountHistoryItem {
  public cardHolder: NamedCardHolder;
  public inOutState: number;
  public room: Room;
  public timestamp: string;
  public uuid: string;
  public event: HistoryEvent;
}

export class SessionHistory {
  public room: Room;
  public sessionEnd: string;
  public sessionStart: string;
}
