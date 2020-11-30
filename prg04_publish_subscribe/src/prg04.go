/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg04 - Publish Subscribe Simulation
 * Student Name: Austin Gailey
 */

package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Pubsub struct {
	mu     sync.RWMutex
	subs   map[string][]chan string
	closed bool
}

func NewPubsub() *Pubsub {
	ps := &Pubsub{}
	ps.subs = make(map[string][]chan string)
	return ps
}

var wg sync.WaitGroup

// TODOd: creates and returns a new channel on a given topic, updating the Pubsub struct
func (ps *Pubsub) Subscribe(topic string) <-chan string {
	ps.mu.Lock()
	defer ps.mu.Unlock()
	ch := make(chan string, 1)
	ps.subs[topic] = append(ps.subs[topic], ch)
	return ch
}

// TODOd: writes the given message on all the channels associated with the given topic
func (ps *Pubsub) Publish(topic string, msg string) {
	ps.mu.RLock()
	defer ps.mu.RUnlock()
	if ps.closed {
		return
	}
	for _, ch := range ps.subs[topic] {
		go func(ch chan string) {
			ch <- msg
		}(ch)
	}
}

// TODOd sends messages taken from a given array of message, one at a time and at random intervals, to all topic subscribers
func publisher(ps *Pubsub, topic string, msgs []string) {
	time.Sleep(10 * time.Second)
	for _, msg := range msgs {
		time.Sleep(time.Duration(rand.Intn(5)) * time.Second)
		//fmt.Println(msg)
		ps.Publish(topic, msg)
	}
	wg.Done()
}

// TODOd: reads and displays all messages received from a particular topic
func subscriber(ps *Pubsub, name string, topic string) {
	ch := ps.Subscribe(topic)
	for {
		if msg, ok := <-ch; ok {
			fmt.Println(name + " Received: " + msg)
		} else {
			break
		}
	}
}

func main() {

	// TODOd: create the ps struct
	ps := NewPubsub()

	// TODOd: create the arrays of messages to be sent on each topic
	carMessages := []string{
		"Cars come in many different makes and models.",
		"The Subaru Outback is the best selling car in Colorado.",
		"Tesla cars are capable of full self driving."}
	gameMessages := []string{
		"The videogame industry is worth $90 billion.",
		"Cyberpunk 2077 is the most anticipated game of 2020.",
		"Fortnite changed the way companies generated in app purchases."}

	// TODOd: set wait group to 2 (# of publishers)
	wg.Add(2)
	// TODOd: create the publisher goroutines
	fmt.Println("Giving subscribers a chance to subscribe.")
	fmt.Println("Please wait 10 seconds for messages to start arriving...")
	go publisher(ps, "cars", carMessages)
	go publisher(ps, "games", gameMessages)

	// TODOd: create the subscriber goroutines
	go subscriber(ps, "John", "games")
	go subscriber(ps, "Mary", "cars")
	go subscriber(ps, "Mary", "games")
	// TODOd: wait for all publishers to be done
	wg.Wait()
}
