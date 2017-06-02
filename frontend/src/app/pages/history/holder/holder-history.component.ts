import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UnsubscribableComponent } from '../../../theme/unsubscribable.component';
import { AccountHistoryItem, SessionDetail, SessionHistory } from '../../../models/history/history.models';
import { HistoryClient } from '../history.client';
import * as moment from 'moment';
import { Room } from '../../../models/rooms/room.models';

@Component({
  selector: 'holder-history',
  styleUrls: ['./holder-history.scss'],
  templateUrl: './holder-history.component.html'
})
export class HolderHistoryComponent extends UnsubscribableComponent implements OnInit {

  public rows: AccountHistoryItem[];
  public uuid: string;
  public showCalendar: boolean = false;
  public calendarConfiguration: any;
  public cardHolderName: string = '';

  public get currentPlace(): string {
    if (!this._currentPlace || !this._currentPlace.uuid) {
      return 'Outside';
    } else {
      return `${this._currentPlace.name} in the ${this._currentPlace.building}`;
    }
  }

  private _currentPlace: Room = null;

  constructor(private currentRoute: ActivatedRoute,
              private historyClient: HistoryClient) {
    super();
  }

  public ngOnInit(): void {
    this.subscribers.push(this.currentRoute.params.subscribe(params => {
      this.uuid = params['uuid'];
      this.loading = true;
      this.getSessions();
      this.subscribers.push(this.historyClient.findHolder(this.uuid).subscribe((result: Room) => {
        this._currentPlace = result;
      }));
    }));
  }

  public onCalendarReady(data): void {

  }

  private getSessions() {
    this.loading = true;
    this.subscribers.push(
      this.historyClient.getSessions(this.uuid)
        .finally(() => this.loading = false)
        .subscribe((data: SessionDetail) => {
          this.cardHolderName = data.fullName;
          this.initCalendar(data.sessions);
        }));
  }

  private initCalendar(history: SessionHistory[]): void {
    this.calendarConfiguration = {
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay'
      },
      defaultDate: moment(),
      selectable: true,
      selectHelper: true,
      editable: false,
      eventLimit: true,
      timeFormat: 'HH:mm'
    };
    this.calendarConfiguration.events = this.generateEvents(history);
    this.showCalendar = true;
  }

  private generateEvents(history: SessionHistory[] = []): any {
    return history.map(item => {
      return {
        title: item.room.name,
        start: moment(item.sessionStart),
        end: moment(item.sessionEnd),
        color: '#fff',
        textColor: '#000',
        borderColor: '#000'
      };
    });
  }

}
