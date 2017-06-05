import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UnsubscribableComponent } from '../../../theme/unsubscribable.component';
import { RoomClient } from '../room.client';
import { Room } from '../../../models/rooms/room.models';


@Component({
  selector: 'room-form',
  templateUrl: './room-form.component.html'
})
export class RoomFormComponent extends UnsubscribableComponent implements OnInit {

  public uuid: string;

  public form: FormGroup;

  constructor(private currentRoute: ActivatedRoute,
              private router: Router,
              private fb: FormBuilder,
              private roomClient: RoomClient) {
    super();
  }

  public ngOnInit(): void {
    this.uuid = this.currentRoute.snapshot.params['uuid'];
    if (this.uuid) {
      this.loading = true;
      this.subscribers.push(this.roomClient.getRoom(this.uuid)
        .finally(() => this.loading = false)
        .subscribe((result: Room) => {
          this.initForm(result);
        }, () => {
          this.initForm();
        }));
    } else {
      this.initForm();
    }
  }

  public createRoom() {
    if (this.uuid) {
      this.roomClient.updateRoom(this.uuid, this.form.getRawValue()).subscribe(() => {
        this.router.navigate(['/pages/rooms']);
      });
    } else {
      this.roomClient.createRoom(this.form.getRawValue()).subscribe(() => {
        this.router.navigate(['/pages/rooms']);
      });
    }
  }

  private initForm(obj: Room = new Room()): void {
    this.form = this.fb.group({
      name: [obj.name || '', Validators.required],
      building: [obj.building || '', Validators.required],
      floor: [obj.floor || 0]
    })
  }
}
