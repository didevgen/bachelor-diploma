import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UnsubscribableComponent } from '../../../theme/unsubscribable.component';
import { AccountHistoryItem, SessionHistory } from '../../../models/history/history.models';
import { HistoryClient } from '../history.client';
import * as moment from 'moment';

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

  constructor(private currentRoute: ActivatedRoute,
              private historyClient: HistoryClient) {
    super();
  }

  public ngOnInit(): void {
    this.subscribers.push(this.currentRoute.params.subscribe(params => {
      this.uuid = params['uuid'];
      this.loading = true;
      this.getSessions();
    }));
  }

  public onCalendarReady(data): void {

  }

  private getSessions() {
    this.loading = true;
    this.subscribers.push(
      this.historyClient.getSessions(this.uuid)
        .finally(() => this.loading = false)
        .subscribe((history: SessionHistory[]) => {
          this.initCalendar(history);
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
      eventLimit: true
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
